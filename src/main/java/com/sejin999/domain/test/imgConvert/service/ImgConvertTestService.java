package com.sejin999.domain.test.imgConvert.service;

import com.sejin999.domain.global.util.Base64ToImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ImgConvertTestService {
    private final Base64ToImageUtil base64ToImageUtil;

    public ImgConvertTestService(Base64ToImageUtil base64ToImageUtil) {
        this.base64ToImageUtil = base64ToImageUtil;
    }

    public void testImgConvertService(String base64Data){
        byte[] test =base64ToImageUtil.decodeBase64ToBytes(base64Data);
        String path = "C:\\Users\\SeJin\\Desktop\\sejin-999-web\\sejin-999-web\\src\\main\\resources\\static\\IMG\\test";
        base64ToImageUtil.saveImageFromBytes(test , path);
    }
}
