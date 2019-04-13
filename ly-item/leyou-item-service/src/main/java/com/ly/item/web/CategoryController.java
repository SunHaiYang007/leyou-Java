package com.ly.item.web;

import com.ly.common.enums.MyExceptionEnums;
import com.ly.common.exception.MyException;
import com.ly.item.service.CategoryService;
import com.ly.pojo.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by HXin on 2019/1/18.
 */
@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryCategoryByPid(@RequestParam(value = "pid",defaultValue = "0") Long pid){

        List<Category> categoryList = categoryService.queryCategoryByPid(pid);
        if(CollectionUtils.isEmpty(categoryList)){
            throw new MyException(MyExceptionEnums.CATEGORY_IS_NOT_FOUND);
        }
        return ResponseEntity.ok(categoryList);
    }

    /**
     * 根据分类id 查询分类
     * @param ids
     * @return
     */
    @GetMapping("list/ids")
    public ResponseEntity<List<Category>> queryCategoryByIds(@RequestParam("ids") List<Long> ids){
        return ResponseEntity.ok(categoryService.queryCategoriesByCids(ids));
    }
}
