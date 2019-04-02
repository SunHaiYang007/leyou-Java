package com.ly.common.advice;

import com.ly.common.exception.MyException;
import com.ly.common.vo.MyExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;


/**
 * Created by HXin on 2019/1/16.
 */
@ControllerAdvice
public class MyExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<MyExceptionResult> handleException(MyException e){
        return ResponseEntity.status(e.getMyExceptionEnums().getCode()).body(new MyExceptionResult(e.getMyExceptionEnums()));


    }
}
