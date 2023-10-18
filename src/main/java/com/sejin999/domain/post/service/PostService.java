package com.sejin999.domain.post.service;

import com.sejin999.domain.post.domain.IntroductionPost;
import com.sejin999.domain.post.domain.Post;
import com.sejin999.domain.post.domain.PostDetail;
import com.sejin999.domain.post.repository.DAO.PostDetailDAO;
import com.sejin999.domain.post.repository.DTO.PostDTO;
import com.sejin999.domain.post.repository.DTO.PostDetailDTO;
import com.sejin999.domain.post.repository.IntroductionPostJPARepository;
import com.sejin999.domain.post.repository.PostDetailJPARepository;
import com.sejin999.domain.post.repository.PostJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PostService {

    private final PostJPARepository postJPARepository;
    private final IntroductionPostJPARepository introductionPostJPARepository;
    private final PostDetailJPARepository postDetailJPARepository;

    public PostService(PostJPARepository postJPARepository, IntroductionPostJPARepository introductionPostJPARepository, PostDetailJPARepository postDetailJPARepository) {
        this.postJPARepository = postJPARepository;
        this.introductionPostJPARepository = introductionPostJPARepository;
        this.postDetailJPARepository = postDetailJPARepository;
    }

    public boolean post_exists_service(Long postSeq){
        return postJPARepository.existsBySeq(postSeq);
    }

    public PostDetailDAO post_read_service(Long postSeq){
        PostDetailDAO postDetailDAO;
        try {
            Post post = postJPARepository.findBySeq(postSeq);
            postDetailDAO = mapPostToPostDetailDAO(post);
        }catch (DataIntegrityViolationException e) {
            // 데이터베이스 무결성 제약 조건 위반 - 키 중복  or 조건 위배
            postDetailDAO = null;
            log.warn("index_create_service : {}");
        } catch (JpaSystemException e) {
            postDetailDAO = null;
            // JPA 연동 중 문제 발생
            log.warn("index_create_service : {}");
        } catch (DataAccessException e) {
            // 데이터 액세스 오류
            postDetailDAO = null;
            log.warn("index_create_service : {}");
        } catch (Exception e) {
            postDetailDAO = null;
            // 다른 모든 예외 처리
            log.warn(e.getMessage());
        }
        return postDetailDAO;

    }

    private PostDetailDAO mapPostToPostDetailDAO(Post post){
        PostDetailDAO postDetailDAO = new PostDetailDAO();
        postDetailDAO.setPostSeq(post.getSeq());
        postDetailDAO.setTitle(post.getTitle());
        postDetailDAO.setCreateTime(post.getIsCreated());
        postDetailDAO.setLastUpdateTime(post.getIsUpdated());

        return postDetailDAO;
    }


    public String post_create_service(PostDTO postDTO){
        log.info("post_create_service >> start");
        String return_text;
        Post getPost;
        try {
            //find introduction object;
            IntroductionPost introductionPost =
                    introductionPostJPARepository.findBySeq(postDTO.getIntroSeq());
            //save post
            getPost = postJPARepository.save(
                    Post.builder()
                            .title(introductionPost.getTitle())
                            .introductionPostIndex(introductionPost)
                            .build()
            );
            log.info("post create_success > {}",postDTO.getTitle());

            //post detail creat;

            List<PostDetailDTO> postDetailDTOList =
                    postDTO.getPostDetailDTOList();


            if(!(postDetailDTOList.isEmpty())){
                //포스트 내용을 적음
                for(PostDetailDTO postDetailDTO : postDetailDTOList){
                    postDetailJPARepository.save(
                            PostDetail.builder()
                                    .content(postDetailDTO.getContent())
                                    .postImgURL(postDetailDTO.getPostImgURL())
                                    .post(getPost)
                                    .build()
                    );
                }


            }

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
