package com.sejin999.domain.test.s3.controller;

import com.sejin999.domain.test.s3.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/v1/test")
public class S3Controller {
    @Autowired
    private S3Service s3Service;

    @PostMapping("/uploadFileToS3")
    public String uploadFileToS3(@RequestParam("file") MultipartFile file) {
        try {
            log.info("s3 upload test start");
            s3Service.uploadFile(file);
            return "File uploaded successfully to S3";
        } catch (Exception e) {
            return "Failed to upload file to S3: " + e.getMessage();
        }
    }
}
