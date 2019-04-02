package com.ly.com.ly.service;

import com.ly.common.enums.MyExceptionEnums;
import com.ly.common.exception.MyException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by HXin on 2019/4/1.
 */
@Service
public class UpLoadService {
    private final List<String> imageNameList = Arrays.asList("image/jpeg","image/png","image/jpg");
    public String uploadImage(MultipartFile file) {
        try {
        //校验
        if(!imageNameList.contains(file.getContentType())){
            throw new MyException(MyExceptionEnums.INVALID_IMAGE);
        }
        BufferedImage read = ImageIO.read(file.getInputStream());
        if(read==null){
            throw new MyException(MyExceptionEnums.INVALID_IMAGE);
        }
            //获取存储目标路径
        File dest = new File("F:\\leyou-idea-code\\leyou\\image_upload",file.getOriginalFilename());
        //存储

            file.transferTo(dest);
            //返回结果
            return "i8888888888888888888888888888888888e.leyou.com";
        } catch (IOException e) {
            e.printStackTrace();
            throw new MyException(MyExceptionEnums.UPLOAD_IMAGE_ERROR);
        }
    }
}
