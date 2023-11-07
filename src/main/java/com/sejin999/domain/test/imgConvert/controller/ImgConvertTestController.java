package com.sejin999.domain.test.imgConvert.controller;

import com.sejin999.domain.test.imgConvert.service.ImgConvertTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/test")
public class ImgConvertTestController {
    private final ImgConvertTestService imgConvertTestService;


    public ImgConvertTestController(ImgConvertTestService imgConvertTestService) {
        this.imgConvertTestService = imgConvertTestService;
    }

    @PostMapping("/convertTest")
    public String uploadFileToS3(@RequestBody String base64Data) {
        imgConvertTestService.testImgConvertService(base64Data);
        return "test";
    }
}
