package com.sejin999.domain.test.s3.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class S3Service {
    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private String bucket;

    // MultipartFile을 S3에 업로드하는 메서드
    public void uploadFile(MultipartFile file) throws IOException {

        String fileName=file.getOriginalFilename();
        String fileUrl= "https://" + bucket + "/test" +fileName;
        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        s3Client.putObject(bucket,fileName,file.getInputStream(),metadata);
    }

    // MultipartFile을 File로 변환하는 유틸리티 메서드
    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        file.transferTo(convertedFile);
        return convertedFile;
    }
}
