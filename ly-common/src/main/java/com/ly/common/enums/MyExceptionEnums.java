package com.ly.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by HXin on 2019/1/16.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum MyExceptionEnums {
    PRICE_CAN_NOT_BE_NULL(400,"价格不能为空"),
    CATEGORY_IS_NOT_FOUND(404,"没有找到商品分类信息"),
    BRAND_IS_NOT_FOUND(404,"品牌分类信息未找到"),
    INSERT_BRAND_ERROR(500,"新增品牌失败"),
    UPLOAD_IMAGE_ERROR(500,"上传图片失败"),
    INVALID_IMAGE(500,"非法的文件类型"),
    SPECIFICATION_GROUP_IS_NOT_FOUND(404,"参数规格组没有找到"),
    SPECIFICATION_PARAM_IS_NOT_FOUND(404,"参数规格没有找到"),
    GOODS_IS_NOT_FOUND(404,"查找的商品不存在"),
    SPU_IS_NOT_FOUND(404,"查找的商品SPU不存在"),
    SKU_IS_NOT_FOUND(404,"查找的商品SKU不存在"),
    SAVE_GOODS_ERROR(500,"保存商品信息失败"),
    UPDATE_SPU_ERROR(500,"更新商品SPU失败"),
    UPDATE_SPUDETAIL_ERROR(500,"更新商品详情失败"),
    SAVE_SPUDETAIL_ERROR(500,"保存商品详情失败"),
    DELETE_SKU_ERROR(500,"删除商品SKU失败"),
    DELETE_STOCK_ERROR(500,"删除商品库存失败"),
    ;
    private int code;
    private String message;
}
