package com.sejin999.domain.post.controller;

import com.sejin999.domain.post.repository.DAO.PostDetailDAO;
import com.sejin999.domain.post.repository.DTO.PostDTO;
import com.sejin999.domain.post.service.IntroductionPostService;
import com.sejin999.domain.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/post")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostController {

    private final PostService postService;
    private final IntroductionPostService introductionPostService;

    public PostController(PostService postService, IntroductionPostService introductionPostService) {
        this.postService = postService;
        this.introductionPostService = introductionPostService;
    }

    @GetMapping("/create_post")
    public ResponseEntity post_create_controller(@RequestBody PostDTO postDTO){
       if(introductionPostService.intro_exists(postDTO.getIntroSeq()) || postDTO.isCreateValid()) {
           //존재
           String return_text = postService.post_create_service(postDTO);
           if(return_text.equals("success")){
               //성공
               return ResponseEntity.ok(return_text);
           }else{
               //실패
               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(return_text);
           }
       }else{
           //없음
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("요청값이 문제가 있습니다.");
       }
    }

    @GetMapping("/{postSeq}")
    public ResponseEntity post_read_controller(@PathVariable Long postSeq){
            if(postService.post_exists_service(postSeq)){
                PostDetailDAO postDetailDAO = 
                        postService.post_read_service(postSeq);
                if(postDetailDAO !=null){
                    return ResponseEntity.ok(postDetailDAO);
                }else{
                    //null -> 오류
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server Error");
                }
               
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("요청값이 문제가 있습니다.");
            }
    }


}
