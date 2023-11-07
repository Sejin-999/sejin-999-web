package com.sejin999.domain.Image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sejin999.domain.Image.repository.ImageMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.random.RandomGenerator;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final ImageMongoRepository imageMongoRepository;
    @Autowired
    private AmazonS3 s3Client;
    @Autowired
    private String bucket;

    public String upload_img_service(MultipartFile file , String functionName) throws IOException {
        /**
         * img upload - s3
         * success
         * random 번호 (12자리)생성 -> 이미 존재하는 경우 재생성 -> 없는 경우에만
         * random 번호 부여
         * img data (s3 URL , random 번호) 저장 - mongo Db
         */
        uploadFile(file,functionName);



        return null;
    }

    private String random_Tag_generator(){
        /**
         * random 번호 생성 -> 존재확인
         * -> 존재하는 경우 -> 재생성
         * -> 없는 경우 반환
         */
        String randomTag;

        do {
            randomTag = make_random();
        } while (imageMongoRepository.existsByRandomTag(randomTag));
        
        log.info("return random tag");
        return randomTag;


    }
    private String make_random(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(12);

        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }

        String randomString = sb.toString();

        return randomString;
    }

    // MultipartFile을 S3에 업로드하는 메서드
    private String uploadFile(MultipartFile file , String upload_folderName) throws IOException {
        String folderName =  upload_folderName;
        String fileName=file.getOriginalFilename();
        String uploadFile = folderName+"/"+fileName;
        //String fileUrl= "https://sejin-999-web-bucket.s3.ap-northeast-2.amazonaws.com/"+folderName+"/"+fileName;
        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        s3Client.putObject(bucket,uploadFile,file.getInputStream(),metadata);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, uploadFile);
        URL fileUrl = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        log.info("file URL -> {}" , fileUrl);
        String return_fileURL = String.valueOf(fileUrl);
        return return_fileURL;
    }
}
