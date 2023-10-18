package com.sejin999.domain.post.service;

import com.sejin999.domain.post.domain.Index;
import com.sejin999.domain.post.domain.IntroductionPost;
import com.sejin999.domain.post.domain.Post;
import com.sejin999.domain.post.repository.DAO.IntroDetailDAO;
import com.sejin999.domain.post.repository.DAO.PostDetailDAO;
import com.sejin999.domain.post.repository.DAO.PostListDAO;
import com.sejin999.domain.post.repository.DTO.IntroDTO;
import com.sejin999.domain.post.repository.IndexJPARepository;
import com.sejin999.domain.post.repository.IntroductionPostJPARepository;
import com.sejin999.domain.post.repository.PostJPARepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IntroductionPostService {
    private final IntroductionPostJPARepository introductionPostJPARepository;
    private final IndexJPARepository indexJPARepository;
    private final PostJPARepository postJPARepository;
    public IntroductionPostService(IntroductionPostJPARepository introductionPostJPARepository, IndexJPARepository indexJPARepository, PostJPARepository postJPARepository) {
        this.introductionPostJPARepository = introductionPostJPARepository;
        this.indexJPARepository = indexJPARepository;
        this.postJPARepository = postJPARepository;
    }
    public boolean intro_exists(Long introSeq){
        return introductionPostJPARepository.existsBySeq(introSeq);
    }

    public IntroDetailDAO intro_read_detail_service(Long introSeq){
        log.info("intro_read_detail_service >> start");
        IntroductionPost introductionPost = introductionPostJPARepository.findBySeq(introSeq);
        IntroDetailDAO introDetailDAO;

        if(introductionPost != null){
            introDetailDAO = mapIntroToIntroDetailDAO(introductionPost);
            //make post List
            introDetailDAO.setPostList(makePostList(introductionPost));
            return introDetailDAO;

        }else{
            return null;
        }
    }

    private IntroDetailDAO mapIntroToIntroDetailDAO(IntroductionPost introductionPost) {
        IntroDetailDAO introDetailDAO =new IntroDetailDAO();
        introDetailDAO.setIntroSeq(introductionPost.getSeq());
        introDetailDAO.setTitle(introductionPost.getTitle());
        introDetailDAO.setContent(introductionPost.getContent());
        introDetailDAO.setLastUpdateTime(introductionPost.getIsUpdated());
        introDetailDAO.setImageURL(introductionPost.getImageURL());
        return introDetailDAO;
    }

    private List<PostListDAO> makePostList(IntroductionPost introductionPost){
        List<PostListDAO> postListDAO;
        List<Post> postList = postJPARepository.findByIntroductionPostIndex(introductionPost);
        //map postListDAO
        postListDAO = postList.stream()
                .map(this::mapPostToPostListDAO)
                .collect(Collectors.toList());
        return postListDAO;
    }

    private PostListDAO mapPostToPostListDAO(Post post){
        PostListDAO postListDAO = new PostListDAO();
        postListDAO.setPostSeq(post.getSeq());
        postListDAO.setTitle(post.getTitle());
        postListDAO.setLastUpdateTime(post.getIsUpdated());
        return postListDAO;
    }

    @Transactional
    public String intro_update_service(Long introSeq , IntroDTO introDTO){
        String return_text;

        IntroductionPost introductionPost = introductionPostJPARepository.findBySeq(introSeq);
        if(introductionPost !=null){
            if (introDTO.getTitle() != null && !introDTO.getTitle().trim().isEmpty()) {
                introductionPost.setTitle(introDTO.getTitle());
            }

            if (introDTO.getImageURL() != null && !introDTO.getImageURL().trim().isEmpty()) {
                introductionPost.setImageURL(introDTO.getImageURL());
            }

            if (introDTO.getContent() != null && !introDTO.getContent().trim().isEmpty()) {
                introductionPost.setContent(introDTO.getContent());
            }
            try{
                introductionPostJPARepository.save(introductionPost);
                return_text="success";
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


        }else{
            return_text = "Intro SEQ Wrong";
        }

        return return_text;
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
