package com.ly.item.web;

import com.ly.item.service.SpecificationService;
import com.ly.pojo.Specification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<Specification>> querySpecByCid(@PathVariable("cid") Long cid){
        List specList = specificationService.querySpecByCid(cid);
        return ResponseEntity.ok(specList);
    }
}
