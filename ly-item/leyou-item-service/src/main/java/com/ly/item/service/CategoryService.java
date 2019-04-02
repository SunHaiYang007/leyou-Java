package com.ly.item.service;

import com.ly.item.mapper.CategoryMapper;
import com.ly.pojo.Category;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by HXin on 2019/1/18.
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryCategoryByPid(Long pid){
        Category t = new Category();
        t.setParentId(pid);
        List<Category> categoryList = categoryMapper.select(t);
        return categoryList;
    }
}