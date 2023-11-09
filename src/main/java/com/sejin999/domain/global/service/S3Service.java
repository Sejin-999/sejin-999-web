package com.sejin999.domain.global.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
@Slf4j
public class S3Service {
    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    private String bucket;

    // MultipartFile을 S3에 업로드하는 메서드
    public String uploadFile(MultipartFile file , String folderName) throws IOException {
        String fileName=file.getOriginalFilename();
        String uploadFile = folderName+"/"+fileName;
        //String fileUrl= "https://sejin-999-web-bucket.s3.ap-northeast-2.amazonaws.com/"+folderName+"/"+fileName;
        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        try{
            s3Client.putObject(bucket,uploadFile,file.getInputStream(),metadata);

            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, uploadFile);
            URL fileUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
            log.info("file URL -> {}" , fileUrl);

            String return_url = String.valueOf(fileUrl);

            return return_url;
        }catch (Exception e){
            log.warn("------ S3upload fail ------ \n{}",e);
            return "fail";
        }

    }

    // MultipartFile을 File로 변환하는 유틸리티 메서드
    private File convertMultiPartFileToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(file.getOriginalFilename());
        file.transferTo(convertedFile);
        return convertedFile;
    }
}
