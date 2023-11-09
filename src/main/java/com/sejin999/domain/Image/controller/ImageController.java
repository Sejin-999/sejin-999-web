package com.sejin999.domain.Image.controller;

import com.sejin999.domain.Image.service.ImageService;
import com.sejin999.domain.post.repository.DTO.IndexDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v1/image")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class ImageController {
    /**
     * 이미지 업로드 -> 임시 랜덤번호 부여 및 이미지 URL ,랜덤번호 noSQL -> mongo db에 store
     */
    private final ImageService imageService;
    @GetMapping("/create_image/{function}")
    public ResponseEntity index_create_controller(@RequestParam("file") MultipartFile file , @PathVariable String function){

        if(file.isEmpty()){
            log.warn("img not exists");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not exists file");
        }else{
            log.info("IMG UPLOAD START");
            try {
                imageService.upload_img_service(file , function);
            }catch (Exception e) {
                log.warn("img upload error \n {}" , e);
                return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("not exists file");
            }
        }


        return null;
    }
}
