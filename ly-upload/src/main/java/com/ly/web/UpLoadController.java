package com.ly.web;


import com.ly.common.enums.MyExceptionEnums;
import com.ly.common.exception.MyException;
import com.ly.service.UpLoadService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by HXin on 2019/4/1.
 */
@RestController
@RequestMapping("upload")
public class UpLoadController {

    @Autowired
    private UpLoadService upLoadService;

    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file){
        String url = upLoadService.upload(file);
        if (StringUtils.isBlank(url)){
            throw new MyException(MyExceptionEnums.UPLOAD_IMAGE_ERROR);
        }
        return ResponseEntity.ok(url);
    }
}
