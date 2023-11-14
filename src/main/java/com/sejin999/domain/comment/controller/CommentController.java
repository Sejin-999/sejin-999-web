package com.sejin999.domain.comment.controller;

import com.sejin999.domain.comment.domain.CommentType;
import com.sejin999.domain.comment.repostiory.DTO.CommentDTO;
import com.sejin999.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/{type}")
    public ResponseEntity comment_upload_controller
            (@RequestBody CommentDTO commentDTO, @PathVariable String type){
        log.info("----comment_upload_controller----");
        if(commentDTO.isCreateValid(type)){
            //검사 통과
            String return_text = String.valueOf(commentService.comment_upload_Service(commentDTO , type));
            log.info("comment upload text -> {}",return_text);
            if(return_text.equals("Master comment uploaded.") || return_text.equals("Slave comment uploaded.")){
                return ResponseEntity.ok(return_text);
            } else if (return_text.equals("db access error")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(return_text);
            } else{
                //실패
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(return_text);
            }
        }else{
            //검사 통과 실패
            log.info("----comment_upload_controller----\n검증 실패.. 확인 바람..");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("검증 실패 , null or 요구사항 확인 필요");
        }

    }
}
