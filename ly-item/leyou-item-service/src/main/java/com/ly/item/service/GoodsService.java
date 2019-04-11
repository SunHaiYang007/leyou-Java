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
        List<Stock> stockList = new ArrayList<>();
        for (Sku sku : skus) {
            sku.setCreate_time(new Date());
            sku.setLast_update_time(sku.getCreate_time());
            sku.setSpuId(spu.getId());
            count = skuMapper.insert(sku);
            if (count<1){
                throw new MyException(MyExceptionEnums.SAVE_GOODS_ERROR);
            }
            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stockList.add(stock);
        }
        //存储stock   批量存储
        count = stockMapper.insertList(stockList);
        if (count<1){
            throw new MyException(MyExceptionEnums.SAVE_GOODS_ERROR);
        }
    }
}
