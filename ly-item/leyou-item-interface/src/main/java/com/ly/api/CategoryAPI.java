package com.ly.api;


import com.ly.pojo.Category;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by HXin on 2019/4/13.
 */
@RequestMapping("category")
public interface CategoryAPI {
    @GetMapping("list")
    List<Category> queryCategoryByPid(@RequestParam(value = "pid",defaultValue = "0") Long pid);

    /**
     * 根据分类id 查询分类
     * @param ids
     * @return
     */
    @GetMapping("list/ids")
    List<Category> queryCategoryByIds(@RequestParam("ids") List<Long> ids);
}
