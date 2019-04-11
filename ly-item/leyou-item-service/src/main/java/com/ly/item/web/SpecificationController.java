package com.ly.item.web;

import com.ly.common.enums.MyExceptionEnums;
import com.ly.common.exception.MyException;
import com.ly.item.service.SpecificationService;
import com.ly.pojo.SpecGroup;
import com.ly.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by HXin on 2019/4/3.
 */
@RestController
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecByCid(@PathVariable("cid") Long cid){
        List specList = specificationService.querySpecByCid(cid);
        return ResponseEntity.ok(specList);
    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamByGid(
            @RequestParam(name = "gid" ,required = false) Long gid,
            @RequestParam(name = "cid",required = false) Long cid,
            @RequestParam(name = "searching",required = false) Boolean searching,
            @RequestParam(name = "generic",required = false) Boolean generic
    ){
        List<SpecParam> params= specificationService.queryParamByGid(gid,cid,searching,generic);
        if (CollectionUtils.isEmpty(params)){
            throw  new MyException(MyExceptionEnums.SPECIFICATION_PARAM_IS_NOT_FOUND);
        }
        return ResponseEntity.ok(params);
    }
}
