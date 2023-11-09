package com.sejin999.domain.Image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.sejin999.domain.Image.domain.ImageData;
import com.sejin999.domain.Image.repository.ImageMongoRepository;
import com.sejin999.domain.global.service.S3Service;
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
    private final S3Service s3Service;

    public String deleted_imgTag_service(String imgTag){
        String return_text;
        try {
            imageMongoRepository.deleteByRandomTag(imgTag);

            if(imageMongoRepository.existsByRandomTag(imgTag)){
                //이미지가 존재 -> 삭제 되지않음
                log.warn("mongo imgTag deleted fail");
                return_text = "이미지 삭제 실패 -- 이유.. 개발자에게 연락바람";
            }else{
                return_text = "success";
            }
            
        }catch (Exception e){
            log.warn("mongo imgTag deleted fail \n{}",e);
            return_text = "이미지 삭제 실패 -- 이유.. 확인가능";
        }

        return return_text;
    }
    
    public String find_img_url_service(String imgTag){
        /**
         * 이미지 태그를 받아 몽고 db에 저장된 s3URL을 전달하는 함수
         */

        if(imageMongoRepository.existsByRandomTag(imgTag)){
            //존재
            ImageData imageData = imageMongoRepository.findByRandomTag(imgTag);
            return imageData.getImageURL();
        }else{
            //존재하지 않음
            return  "not exists";
        }
    }

    public String upload_img_service(MultipartFile file , String folderName) throws IOException {
        /**
         * img upload - s3
         * success
         * random 번호 (12자리)생성 -> 이미 존재하는 경우 재생성 -> 없는 경우에만
         * random 번호 부여
         * img data (s3 URL , random 번호) 저장 - mongo Db
         */
        String tempURL = s3Service.uploadFile(file, folderName);
        if(tempURL.equals("fail")){
            return "s3Upload fail";
        }else{
            String randomTag = random_Tag_generator();
            ImageData imageData = new ImageData();
            imageData.setImageURL(tempURL);
            imageData.setRandomTag(randomTag);

            try {
                imageMongoRepository.save(imageData);
                log.info("{} is success" , randomTag);
                return randomTag;
            }catch (Exception e){
                log.warn("-----mongo db upload error------ \n {}" ,e );
                return "mongoDB upload fail";
            }

        }
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
}
