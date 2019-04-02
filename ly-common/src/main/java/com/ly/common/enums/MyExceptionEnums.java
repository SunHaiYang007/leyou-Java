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
    ;
    private int code;
    private String message;
}
