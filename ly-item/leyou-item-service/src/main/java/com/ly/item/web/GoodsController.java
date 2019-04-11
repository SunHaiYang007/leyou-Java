package com.ly.item.web;

import com.ly.common.enums.MyExceptionEnums;
import com.ly.common.exception.MyException;
import com.ly.common.vo.PageResult;
import com.ly.item.service.GoodsService;
import com.ly.pojo.Spu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by HXin on 2019/4/10.
 */
@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<Spu>> queryGoodsByPage(
            @RequestParam(value = "page",defaultValue = "1") int page,
            @RequestParam(value = "rows",defaultValue = "5") int rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key",required = false) String key
    ){
        PageResult<Spu> spuPageResult = goodsService.queryGoodsByPage(page, rows, saleable, key);
        if (spuPageResult==null){
            throw new MyException(MyExceptionEnums.GOODS_IS_NOT_FOUND);
        }

        return ResponseEntity.ok(spuPageResult);
    }

    @PostMapping("goods")
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu){    //返回数据是json  记得加requestBody注解  否则拿不到数据
        goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
