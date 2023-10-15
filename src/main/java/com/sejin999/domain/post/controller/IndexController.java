package com.sejin999.domain.post.controller;

import com.sejin999.domain.post.repository.DAO.IndexDAO;
import com.sejin999.domain.post.repository.DAO.IndexDetailDAO;
import com.sejin999.domain.post.repository.DTO.IndexDTO;
import com.sejin999.domain.post.repository.DTO.IndexUpdateDTO;
import com.sejin999.domain.post.service.IndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/index")
public class IndexController {

    private final IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    /**
     *
     * @param indexDTO
     * index Controller
     * @return
     */
    @PostMapping("/create_index")
    public ResponseEntity index_create_controller(@RequestBody IndexDTO indexDTO){
        log.info("index_create_controller >> index {}",indexDTO.getTitle());
        if(!indexDTO.isCreateValid()){
            log.warn("index_create_controller : 검증실패..");
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

    @PostMapping("/update_index")
    public ResponseEntity index_update_controller(@RequestBody IndexUpdateDTO indexUpdateDTO){

        log.info("index_update_controller start seq : {} , title: {} " ,indexUpdateDTO.getIndexSeq() , indexUpdateDTO.getIndexDTO().getTitle() );

        String return_text = indexService.index_update_service(indexUpdateDTO.getIndexSeq(),indexUpdateDTO.getIndexDTO());
        if(return_text.equals("success")){
            return ResponseEntity.ok(return_text);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(return_text);
        }
    }

    @GetMapping("/index_list")
    public ResponseEntity index_read_list_controller(){
        log.info("index_read_list_controller start");
        List<IndexDAO> indexDAOList = indexService.index_read_list_service();

        if(indexDAOList.isEmpty() || indexDAOList == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("cant get list");
        }else{
            return ResponseEntity.ok(indexDAOList);
        }

    }

    @GetMapping("/index_detail/{seq}")
    public ResponseEntity index_read_detail_controller(@PathVariable Long seq){
        log.info("index_read_detail_controller");
        IndexDetailDAO indexDetailDAO = indexService.index_read_detail_service(seq);
        if(!(indexDetailDAO == null)){
            return ResponseEntity.ok(indexDetailDAO);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("cant get content");
        }
    }




}
