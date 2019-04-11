package com.ly.item.service;

import com.ly.common.enums.MyExceptionEnums;
import com.ly.common.exception.MyException;
import com.ly.item.mapper.CategoryMapper;
import com.ly.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by HXin on 2019/1/18.
 */
@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryCategoryByPid(Long pid){
        Category t = new Category();
        t.setParentId(pid);
        List<Category> categoryList = categoryMapper.select(t);
        if (CollectionUtils.isEmpty(categoryList)){
            throw new MyException(MyExceptionEnums.CATEGORY_IS_NOT_FOUND);
        }
        return categoryList;
    }

    public List<Category> queryCategoriesByCids(List cids) {
        List list = categoryMapper.selectByIdList(cids);
        if (CollectionUtils.isEmpty(list)){
            throw new MyException(MyExceptionEnums.CATEGORY_IS_NOT_FOUND);
        }
        return list;
    }
}