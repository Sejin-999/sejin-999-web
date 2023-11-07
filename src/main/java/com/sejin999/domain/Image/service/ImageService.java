package com.sejin999.domain.Image.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageService {

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
}
