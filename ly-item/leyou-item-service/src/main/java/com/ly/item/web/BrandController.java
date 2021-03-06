package com.ly.item.web;

import com.ly.common.enums.MyExceptionEnums;
import com.ly.common.exception.MyException;
import com.ly.item.service.BrandService;
import com.ly.pojo.Brand;
import com.ly.common.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by HXin on 2019/4/1.
 */
@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 分页查询品牌信息
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryByPage(
            @RequestParam(value = "page",defaultValue = "1") Integer page,
            @RequestParam(value = "rows",defaultValue = "5") Integer rows,
            @RequestParam(value ="sortBy",required = false) String sortBy,
            @RequestParam(value = "desc",defaultValue = "false") boolean desc,
            @RequestParam(value = "key",required = false) String key){
            PageResult<Brand> results = brandService.queryByPageAndSort(page,rows,sortBy,desc,key);
            if (results==null||results.getItems().size()==0){
                throw new MyException(MyExceptionEnums.BRAND_IS_NOT_FOUND);
            }
            return ResponseEntity.ok(results);
    }

    /**
     * 保存品牌信息
     * @param brand
     * @param cids
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand, @RequestParam(value = "cids")List<Long> cids){
        brandService.saveBrand(brand,cids);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 根据分类id查询所有品牌
     * @param cid
     * @return
     */
    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> queryBrandByCid(@PathVariable(name = "cid") Long cid){
        List<Brand> list = brandService.queryBrandByCid(cid);
        if (CollectionUtils.isEmpty(list)){
            throw new MyException(MyExceptionEnums.BRAND_IS_NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    /**
     * 根据品牌id查询品牌信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(brandService.queryBrandById(id));
    }

}
