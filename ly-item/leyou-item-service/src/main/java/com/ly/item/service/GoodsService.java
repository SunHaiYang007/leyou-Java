package com.ly.item.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.common.enums.MyExceptionEnums;
import com.ly.common.exception.MyException;
import com.ly.common.vo.PageResult;
import com.ly.item.mapper.SkuMapper;
import com.ly.item.mapper.SpuDetailMapper;
import com.ly.item.mapper.SpuMapper;
import com.ly.item.mapper.StockMapper;
import com.ly.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * Created by HXin on 2019/4/10.
 */
@Service
@Transactional
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

    public PageResult<Spu> queryGoodsByPage(int page, int rows, Boolean saleable, String key) {
        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        if (saleable!=null){
            criteria.andEqualTo("saleable",saleable);
        }
        if (StringUtils.isNotBlank(key)){
            criteria.orLike("title","%"+key+"%");
        }
        //查询 
        List<Spu> spus = spuMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(spus)){
            throw new MyException(MyExceptionEnums.GOODS_IS_NOT_FOUND);
        }
        //对查询的spus进行 cname，bname查询
        LoadCategoryNameAndBrandName(spus);
        PageInfo<Spu> info = new PageInfo<>(spus);
        //返回结果
        return new PageResult<>(info.getTotal(),spus);
    }

    private void LoadCategoryNameAndBrandName(List<Spu> spus) {
        for (Spu spu : spus) {
            //得到bname
            Brand brand = brandService.queryBrandById(spu.getBrandId());
            spu.setBname(brand.getName());
            //得到cname
            Long cid1 = spu.getCid1();
            Long cid2 = spu.getCid2();
            Long cid3 = spu.getCid3();
            List cids = Arrays.asList(cid1,cid2,cid3);
            List<Category> categories = categoryService.queryCategoriesByCids(cids);
            List<String> names = new ArrayList<>();
            for (Category category : categories) {
                names.add(category.getName());
            }
            spu.setCname(StringUtils.join(names,"/"));
        }
    }

    public void saveGoods(Spu spu) {
        //存储spu
        spu.setCreate_time(new Date());
        spu.setLast_update_time(spu.getCreate_time());
        spu.setId(null);
        spu.setSaleable(true);
        spu.setValid(true);

        int count = spuMapper.insert(spu);
        if (count<1){
            throw new MyException(MyExceptionEnums.SAVE_GOODS_ERROR);
        }
        //存储spudetail
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(spu.getId());
        count = spuDetailMapper.insert(spuDetail);
        if (count<1){
            throw new MyException(MyExceptionEnums.SAVE_SPUDETAIL_ERROR);
        }
        //存储sku
        List<Sku> skus = spu.getSkus();
        saveSkuAndStock(skus,spu.getId());
    }

    /**
     * 根据spu_id查询spu
     * @param id
     * @return
     */
    public SpuDetail querySpuDetailById(Long id) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(id);
        if (spuDetail==null){
            throw new MyException(MyExceptionEnums.SPU_IS_NOT_FOUND);
        }
        return spuDetail;
    }

    /**
     * 根据spu id查找sku集合
     * @param id
     * @return
     */
    public List<Sku> querySkuBySpu(Long id) {
        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skus = skuMapper.select(sku);
        for (Sku s : skus) {
            s.setStock(stockMapper.selectByPrimaryKey(s.getId()).getStock());  //将商品的库存量一起查出来
        }
        if (CollectionUtils.isEmpty(skus)){
            throw new MyException(MyExceptionEnums.SKU_IS_NOT_FOUND);
        }
        return skus;
    }

    /**
     * 更新商品信息
     * @param spu
     * @return
     */
    public void updateGoods(Spu spu) {
        //查询sku是否已经存在 如果存在 先删除库存再 删除sku
        Sku sku1 = new Sku();
        sku1.setSpuId(spu.getId());
        List<Sku> old_skus = skuMapper.select(sku1);
        if (!CollectionUtils.isEmpty(old_skus)){
            for (Sku sku : old_skus) {
                Example example = new Example(Stock.class);
                example.createCriteria().andEqualTo("skuId",sku.getId());
                int count = stockMapper.deleteByExample(example);
                if (count<1){
                    throw new MyException(MyExceptionEnums.DELETE_STOCK_ERROR);
                }
            }
            //删除sku
            Sku s = new Sku();
            s.setId(sku1.getId());
            int count = skuMapper.delete(s);
            if (count<1){
                throw new MyException(MyExceptionEnums.DELETE_SKU_ERROR);
            }
        }
        //新增sku 库存
        saveSkuAndStock(spu.getSkus(),spu.getId());
        //更新spu信息
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setCreate_time(null);
        spu.setLast_update_time(new Date());
        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if (count<1){
            throw new MyException(MyExceptionEnums.UPDATE_SPU_ERROR);
        }
        //更新spu详情
        SpuDetail spuDetail = spu.getSpuDetail();
        count = spuDetailMapper.updateByPrimaryKeySelective(spuDetail);
        if (count<1){
            throw new MyException(MyExceptionEnums.UPDATE_SPU_ERROR);
        }

    }

    /**
     * 抽取保存sku 与库存的方法
     * @param skus
     * @param spu_id
     */
    void saveSkuAndStock(List<Sku> skus,Long spu_id){
        List<Stock> stockList = new ArrayList<>();
        for (Sku sku : skus) {
            sku.setCreate_time(new Date());
            sku.setLast_update_time(sku.getCreate_time());
            sku.setSpuId(spu_id);
            int count = skuMapper.insert(sku);
            if (count<1){
                throw new MyException(MyExceptionEnums.SAVE_GOODS_ERROR);
            }
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        }
        //存储stock   批量存储
        int count = stockMapper.insertList(stockList);
        if (count<1){
            throw new MyException(MyExceptionEnums.SAVE_GOODS_ERROR);
        }
    }
}
