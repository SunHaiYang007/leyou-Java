package com.ly.common.exception;

import com.ly.common.enums.MyExceptionEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by HXin on 2019/1/16.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MyException extends RuntimeException {
    private MyExceptionEnums myExceptionEnums;
}
