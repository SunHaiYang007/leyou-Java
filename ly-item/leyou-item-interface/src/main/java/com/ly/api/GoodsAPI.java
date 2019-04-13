package com.ly.api;

import com.ly.common.enums.MyExceptionEnums;
import com.ly.common.exception.MyException;
import com.ly.common.vo.PageResult;
import com.ly.pojo.Sku;
import com.ly.pojo.Spu;
import com.ly.pojo.SpuDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by HXin on 2019/4/13.
 */
public interface GoodsAPI {

    @GetMapping("spu/page")
    PageResult<Spu> queryGoodsByPage(
            @RequestParam(value = "page",defaultValue = "1") int page,
            @RequestParam(value = "rows",defaultValue = "5") int rows,
            @RequestParam(value = "saleable",required = false) Boolean saleable,
            @RequestParam(value = "key",required = false) String key
    );

    @PostMapping("goods")
    void  saveGoods(@RequestBody Spu spu);

    @GetMapping("spu/detail/{spu_id}")
    SpuDetail querySpuDeatilById(@PathVariable(name = "spu_id") Long id);

    /**
     * 根据spu_id查询sku的list
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    List<Sku> querySkuBySpu(@RequestParam(name = "id") Long id);

    /**
     * 更新商品信息
     * @param spu
     * @return
     */
    @PutMapping("goods")
    void updateGoods(@RequestBody Spu spu);

}
