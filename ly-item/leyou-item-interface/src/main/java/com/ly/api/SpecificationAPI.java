package com.ly.api;


import com.ly.pojo.SpecGroup;
import com.ly.pojo.SpecParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by HXin on 2019/4/13.
 */
@RequestMapping("spec")
public interface SpecificationAPI {

    @GetMapping("groups/{cid}")
    List<SpecGroup> querySpecByCid(@PathVariable("cid") Long cid);


    @GetMapping("params")
    List<SpecParam> queryParamByGid(
            @RequestParam(name = "gid" ,required = false) Long gid,
            @RequestParam(name = "cid",required = false) Long cid,
            @RequestParam(name = "searching",required = false) Boolean searching,
            @RequestParam(name = "generic",required = false) Boolean generic
    );
}
