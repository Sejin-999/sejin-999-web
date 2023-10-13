package com.sejin999.domain.post.controller;

import com.sejin999.domain.post.repository.DTO.IndexDTO;
import com.sejin999.domain.post.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/post")
public class postController {

    private final IndexService indexService;

    public postController(IndexService indexService) {
        this.indexService = indexService;
    }
    @PostMapping("/create_post")
    public ResponseEntity index_create_controller(IndexDTO indexDTO){
        if(!indexDTO.isCreateValid()){
            log.info("index_create_controller : 검증실패..");
            //실패
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("검증기준을 통과하지못하였습니다.");
        }else{
            log.info("index_create_controller : 검증성공..");
            //성공
            String return_text = indexService.index_create_service(indexDTO);
            if(return_text.equals("success")){
                return ResponseEntity.ok(return_text);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(return_text);

            }
        }
    }




}
