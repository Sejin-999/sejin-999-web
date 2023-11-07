package com.sejin999.domain.Image.service;

import com.sejin999.domain.Image.repository.ImageMongoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Random;
import java.util.random.RandomGenerator;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {
    private final ImageMongoRepository imageMongoRepository;
    public String upload_img_service(MultipartFile file){
        /**
         * img upload - s3
         * success
         * random 번호 (12자리)생성 -> 이미 존재하는 경우 재생성 -> 없는 경우에만
         * random 번호 부여
         * img data (s3 URL , random 번호) 저장 - mongo Db
         */




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
}
