package com.sejin999.domain.post.controller;

import com.sejin999.domain.Image.service.ImageService;
import com.sejin999.domain.post.repository.DAO.IntroDetailDAO;
import com.sejin999.domain.post.repository.DTO.IntroDTO;
import com.sejin999.domain.post.repository.DTO.IntroUpdateDTO;
import com.sejin999.domain.post.service.IndexService;
import com.sejin999.domain.post.service.IntroductionPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/intro")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class IntroController {
    private final IndexService indexService;
    private final IntroductionPostService introductionPostService;
    private final ImageService imageService;
    public IntroController(IndexService indexService, IntroductionPostService introductionPostService, ImageService imageService) {
        this.indexService = indexService;
        this.introductionPostService = introductionPostService;
        this.imageService = imageService;
    }
    @GetMapping("/create_intro")
    public ResponseEntity intro_create_service(@RequestBody IntroDTO introDTO){
        log.info("intro_create_service start");

        boolean testIndex = indexService.index_exists(introDTO.getIndexSeq());

        if(introDTO.isCreateValid() && testIndex){
            // 검증기준성공 && 인덱스가 존재
            // 성공
            String imgURL = imageService.find_img_url_service(introDTO.getImageTag());
            if(imgURL.equals("not exists")){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("img Tag can't find");
            }else{
                String return_text = introductionPostService.
                        Intro_create_service(introDTO , imgURL);
                if(return_text.equals("success")){
                    String delete_mongo_msg = imageService.deleted_imgTag_service(introDTO.getImageTag());
                    if(delete_mongo_msg.equals("success")){
                        return ResponseEntity.ok(return_text);
                    }else{
                        log.warn("Error -> mongo db deleted");
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(delete_mongo_msg);
                    }
                }else{
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(return_text);

                }
            }
        }else{
            // 실패
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("요청값이 문제가 있습니다.");
        }
    }
    @PostMapping("/update_intro")
    public ResponseEntity intro_update_service(@RequestBody IntroUpdateDTO introUpdateDTO){
        log.info("intro_update_service >> start");

        if(introUpdateDTO.getIntroDTO().isCreateValid() &&
        introductionPostService.intro_exists(introUpdateDTO.getIntroSeq())){
            //success check
            String return_text =
                    introductionPostService.intro_update_service(introUpdateDTO.getIntroSeq()
                            , introUpdateDTO.getIntroDTO());
            if(return_text.equals("success")){
                return ResponseEntity.ok(return_text);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(return_text);
            }
        }else{
            //fail check
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("요청값이 문제가 있습니다.");
        }

    }


    @GetMapping("/intro_list")
    public ResponseEntity intro_read_service(@RequestParam Long introSeq) {
        if (introductionPostService.intro_exists(introSeq)) {
            //존재
            IntroDetailDAO introDetailDAO =
                    introductionPostService.intro_read_detail_service(introSeq);

            return ResponseEntity.ok(introDetailDAO);
        } else {
            //없음
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("옳바르지 않은 요청입니다.");
            }
        }
    }
