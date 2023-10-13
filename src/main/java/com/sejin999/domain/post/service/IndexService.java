package com.sejin999.domain.post.service;

import com.sejin999.domain.post.domain.Index;
import com.sejin999.domain.post.repository.DTO.IndexDTO;
import com.sejin999.domain.post.repository.IndexJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public String index_update_service(Long index_seq , IndexDTO indexDTO){
        log.info("index Update service start ");
        String return_text;

        Index findIndex = indexJPARepository.findById(index_seq)
                .orElseThrow(() -> new RuntimeException("Index not found with id: " + index_seq));

        switch (check_indexUpdate(findIndex , indexDTO)){
            case 0 :
                return_text = "인덱스 업데이트가 없습니다.";
                break;
            case 1:
                findIndex.setTitle(indexDTO.getTitle());
                break;
            case 2:
                findIndex.setContent(indexDTO.getContent());
                break;
            case 3:
                findIndex.setTitle(indexDTO.getTitle());
                findIndex.setContent(indexDTO.getContent());
                break;
            default:
                return_text = "인덱스 체크 검증 오류..";
                break;
        }

        try{
            indexJPARepository.save(findIndex);
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

    private int check_indexUpdate(Index findIndex, IndexDTO indexDTO) {
        /**
         * 0 -> 둘다 NULL
         * 1 -> title 만 업데이트
         * 2 -> Content 만 업데이트
         * 3 -> 둘다 업데이트
         */
        int return_value = 0;

        // 둘 다 null인 경우
        if (isEmptyOrNull(indexDTO.getTitle()) && isEmptyOrNull(indexDTO.getContent())) {
            return_value = 0;
        } else {
            // 둘 중 하나라도 내용이 있는 경우
            return_value = getUpdateType(findIndex, indexDTO);
        }

        log.info("return value : {}", return_value);
        return return_value;
    }

    private int getUpdateType(Index findIndex, IndexDTO indexDTO) {
        int return_value = 0;

        // title만 업데이트
        if (!isEmptyOrNull(indexDTO.getTitle()) && findIndex.getTitle().equals(indexDTO.getTitle())) {
            return_value = 1;
        }

        // content만 업데이트
        if (!isEmptyOrNull(indexDTO.getContent()) && findIndex.getContent().equals(indexDTO.getContent())) {
            return_value = 2;
        }

        // 둘 다 업데이트
        if (return_value == 0) {
            return_value = 3;
        }

        return return_value;
    }

    private boolean isEmptyOrNull(String value) {
        return value == null || value.isEmpty();
    }

}
