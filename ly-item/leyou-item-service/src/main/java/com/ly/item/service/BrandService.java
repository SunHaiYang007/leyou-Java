package com.ly.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ly.common.enums.MyExceptionEnums;
import com.ly.common.exception.MyException;
import com.ly.item.mapper.BrandMapper;
import com.ly.pojo.Brand;
import com.ly.common.vo.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by HXin on 2019/4/1.
 */
@Service
@Transactional
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询品牌分页
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    public PageResult<Brand> queryByPageAndSort(Integer page, Integer rows, String sortBy, boolean desc, String key) {

        //分页
        PageHelper.startPage(page,rows);
        //过滤
        Example example = new Example(Brand.class);
        if (StringUtils.isNoneBlank(key)){
            example.createCriteria().orLike("name","%"+key+"%")
                    .orEqualTo("letter",key.toUpperCase());
        }
        //排序
        if (StringUtils.isNoneBlank(sortBy)) {
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        //查询结果
        List<Brand> brands = brandMapper.selectByExample(example);
        if (brands==null||brands.size()==0){
            throw new MyException(MyExceptionEnums.BRAND_IS_NOT_FOUND);
        }
        PageInfo<Brand> info = new PageInfo<>(brands);
        //返回
        return new PageResult(info.getTotal(),brands);
    }

    @Transactional
    public void saveBrand(Brand brand, List<Long> cids) {
        brand.setId(null);
        int insert = brandMapper.insert(brand);
        if(insert==0){
            throw new MyException(MyExceptionEnums.INSERT_BRAND_ERROR);
        }
        //新增brand_category中间表
        for (Long cid:cids){
            insert = brandMapper.insertCategoryBrand(cid, brand.getId());
            if(insert==0){
                throw new MyException(MyExceptionEnums.INSERT_BRAND_ERROR);
            }
        }
    }

    public Brand queryBrandById(Long brandId) {
        Brand brand = brandMapper.selectByPrimaryKey(brandId);
        if (brand==null){
            throw new MyException(MyExceptionEnums.BRAND_IS_NOT_FOUND);
        }
        return brand;
    }

    public List<Brand> queryBrandByCid(Long cid) {
        List<Brand> list = brandMapper.queryBrandByCid(cid);
        if (CollectionUtils.isEmpty(list)){
            throw new MyException(MyExceptionEnums.BRAND_IS_NOT_FOUND);
        }
        return list;
    }
}
