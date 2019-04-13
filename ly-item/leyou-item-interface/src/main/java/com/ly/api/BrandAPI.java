package com.ly.api;


import com.ly.common.vo.PageResult;
import com.ly.pojo.Brand;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by HXin on 2019/4/13.
 */
@RequestMapping("brand")
public interface BrandAPI {
    @GetMapping("page")
    PageResult<Brand> queryByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value ="sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",defaultValue = "false") boolean desc,
            @RequestParam(value = "key",required = false) String key);

    @PostMapping
    void saveBrand(Brand brand, @RequestParam(value = "cids") List<Long> cids);

    @GetMapping("cid/{cid}")
    List<Brand> queryBrandByCid(@PathVariable(name = "cid") Long cid);

    @GetMapping("{id}")
    Brand queryBrandById(@PathVariable(name = "id") Long id);

}
