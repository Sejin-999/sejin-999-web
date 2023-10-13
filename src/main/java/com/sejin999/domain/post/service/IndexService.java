package com.sejin999.domain.post.service;

import com.sejin999.domain.post.domain.Index;
import com.sejin999.domain.post.repository.DTO.IndexDTO;
import com.sejin999.domain.post.repository.IndexJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IndexService {
    private final IndexJPARepository indexJPARepository;

    public IndexService(IndexJPARepository indexJPARepository) {
        this.indexJPARepository = indexJPARepository;
    }

    /**
     * 기본 기능..
     * index 추가 , 업데이트 , 삭제 , 인덱스 목록 불러오기 , 인덱스 상세 보기
     */

    //index 추가
    public String index_create_service(IndexDTO indexDTO){
        String return_text = "";
        try {
            indexJPARepository.save(
                    Index.builder()
                            .title(indexDTO.getTitle())
                            .content(indexDTO.getContent())
                            .build()
            );
            return_text = "success";
        }catch (DataIntegrityViolationException e) {
            // 데이터베이스 무결성 제약 조건 위반 - 키 중복  or 조건 위배
            return_text = "사용자의 데이터 제대로 검증되지 않았습니다.";
            log.warn("index_create_service : {}" , return_text);
        } catch (JpaSystemException e) {
            // JPA 연동 중 문제 발생
            return_text = "데이터베이스 연동 중 오류가 발생";
            log.warn("index_create_service : {}" , return_text);
        } catch (DataAccessException e) {
            // 데이터 액세스 오류
            return_text = "데이터베이스 액세스 중 오류가 발생";
            log.warn("index_create_service : {}" , return_text);
        } catch (Exception e) {
            // 다른 모든 예외 처리
            return_text = "index_create_service : 알 수 없는 오류가 발생";
            log.warn(e.getMessage());
        }

        return return_text;
    }

    public String index_update_service(Long index_seq , IndexDTO indexDTO){
        String return_text = "";

        Index findIndex = indexJPARepository.findById(index_seq)
                .orElseThrow(() -> new RuntimeException("Index not found with id: " + index_seq));

        if (indexDTO.getTitle() != null && indexDTO.getContent() !=null) {
            findIndex.setTitle(indexDTO.getTitle());
            findIndex.setContent(indexDTO.getContent());
        }else if (indexDTO.getTitle() != null){
            findIndex.setTitle(indexDTO.getTitle());
        }else if (indexDTO.getContent() !=null){
            findIndex.setContent(indexDTO.getContent());
        }
        else{
            return_text = "업데이트 사항이 없습니다.";
        }

    }


}
