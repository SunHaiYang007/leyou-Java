package com.ly.common.vo;

import com.ly.common.enums.MyExceptionEnums;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by HXin on 2019/1/16.
 */
@Data
@NoArgsConstructor
public class MyExceptionResult {
    private int status;
    private String message;
    private Long timestamp;

    public MyExceptionResult (MyExceptionEnums em){
        this.status = em.getCode();
        this.message = em.getMessage();
        this.timestamp = System.currentTimeMillis();
    }
}
