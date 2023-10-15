package com.sejin999.domain.post.service;

import com.sejin999.domain.post.domain.Index;
import com.sejin999.domain.post.domain.IntroductionPost;
import com.sejin999.domain.post.repository.DTO.IntroDTO;
import com.sejin999.domain.post.repository.IndexJPARepository;
import com.sejin999.domain.post.repository.IntroductionPostJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IntroductionPostService {
    private final IntroductionPostJPARepository introductionPostJPARepository;
    private final IndexJPARepository indexJPARepository;

    public IntroductionPostService(IntroductionPostJPARepository introductionPostJPARepository, IndexJPARepository indexJPARepository) {
        this.introductionPostJPARepository = introductionPostJPARepository;
        this.indexJPARepository = indexJPARepository;
    }




    public String Intro_create_service(IntroDTO introDTO){
        log.info("Intro_create_service >> start");
        String return_text;

        try {
            Index index = indexJPARepository.findBySeq(introDTO.getIndexSeq());

            introductionPostJPARepository.save(
                    IntroductionPost.builder()
                            .title(introDTO.getTitle())
                            .content(introDTO.getContent())
                            .imageURL(introDTO.getImageURL())
                            .indexEntity(index)
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

}
