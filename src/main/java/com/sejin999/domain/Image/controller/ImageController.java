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
import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity img_create_controller(@RequestParam("file") MultipartFile file , @PathVariable String function){

        if(file.isEmpty()){
            log.warn("img not exists");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not exists file");
        }else{
            log.info("IMG UPLOAD START");
            try {
                String randomTag = imageService.upload_img_service(file , function);
                return ResponseEntity.ok(randomTag);
            }catch (Exception e) {
                log.warn("img upload error \n {}" , e);
                return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("not exists file");
            }
        }
    }
    @GetMapping("/create_image_several/{function}")
    public ResponseEntity several_img_create_controller(@RequestParam("files") List<MultipartFile> files , @PathVariable String function){

        if (files.isEmpty()) {
            log.warn("이미지가 제공되지 않았습니다.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미지가 제공되지 않았습니다.");
        } else {
            log.info("이미지 업로드 시작");
            List<String> randomTags = new ArrayList<>();

            try {
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        String randomTag = imageService.upload_img_service(file, function);
                        randomTags.add(randomTag);
                    }
                }
                return ResponseEntity.ok(randomTags);
            } catch (Exception e) {
                log.warn("이미지 업로드 오류 \n {}", e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("이미지 업로드 중 오류가 발생했습니다.");
            }
        }
    }


}
