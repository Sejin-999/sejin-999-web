package com.sejin999.domain.post.service;

import com.sejin999.domain.Image.service.ImageService;
import com.sejin999.domain.post.domain.IntroductionPost;
import com.sejin999.domain.post.domain.Post;
import com.sejin999.domain.post.domain.PostDetail;
import com.sejin999.domain.post.repository.DAO.PostDetailDAO;
import com.sejin999.domain.post.repository.DAO.PostDetailListDAO;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {

    private final PostJPARepository postJPARepository;
    private final IntroductionPostJPARepository introductionPostJPARepository;
    private final PostDetailJPARepository postDetailJPARepository;
    private final ImageService imageService;
    public PostService(PostJPARepository postJPARepository, IntroductionPostJPARepository introductionPostJPARepository, PostDetailJPARepository postDetailJPARepository, ImageService imageService) {
        this.postJPARepository = postJPARepository;
        this.introductionPostJPARepository = introductionPostJPARepository;
        this.postDetailJPARepository = postDetailJPARepository;
        this.imageService = imageService;
    }

    public boolean post_exists_service(Long postSeq){
        return postJPARepository.existsBySeq(postSeq);
    }

    public PostDetailDAO post_read_service(Long postSeq){
        PostDetailDAO postDetailDAO;
        try {
            Post post = postJPARepository.findBySeq(postSeq);
            postDetailDAO = mapPostToPostDetailDAO(post);
            
            //postDetailList
            List<PostDetailListDAO> postDetailListDAO;

            List<PostDetail> postDeTailList
                    = postDetailJPARepository.findByPost(post);

            postDetailListDAO = postDeTailList.stream()
                    .map(this::mapPostToPostDetailListDAO)
                    .collect(Collectors.toList());

            //merge

            postDetailDAO.setPostDetailListDAO(postDetailListDAO);

        }catch (DataIntegrityViolationException e) {
            // 데이터베이스 무결성 제약 조건 위반 - seq 값이 Long타입이아닌경우
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

    private PostDetailListDAO mapPostToPostDetailListDAO(PostDetail postDetail){
        PostDetailListDAO postDetailListDAO = new PostDetailListDAO();
        postDetailListDAO.setContent(postDetail.getContent());
        postDetailListDAO.setPostImgURL(postDetail.getPostImgURL());
        return postDetailListDAO;
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
        String return_text = null;
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
                int postErrorFlag = 0; //몽고 dbError 가 발생한 경우 이를 알리기 위한 flag

                //포스트 내용을 적음
                for(PostDetailDTO postDetailDTO : postDetailDTOList){

                    String imgTag  = postDetailDTO.getImgTag();
                    String imgURL  = imageService.find_img_url_service(imgTag);
                    log.info("TAG : {} \nimg URL {}",imgTag,imgURL);
                    if(imgURL.equals("not exists")){
                        //이미지가 존재하지 않음
                        log.info("post_create_service 등록시작.. -> warn -> 이미지 없음");
                        postDetailJPARepository.save(
                                PostDetail.builder()
                                        .content(postDetailDTO.getContent())
                                        .post(getPost)
                                        .build()
                        );
                        
                    }else{
                        //이미지가 존재함
                        log.info("post_create_service 등록시작..");
                        
                        postDetailJPARepository.save(
                                PostDetail.builder()
                                        .content(postDetailDTO.getContent())
                                        .postImgURL(imgURL)
                                        .post(getPost)
                                        .build()
                        );

                        log.info("post_create_service 등록완료 .. mongo 삭제 시작");
                        String return_mongoLog = imageService.deleted_imgTag_service(imgTag);

                        if(!(return_mongoLog.equals("success")) ){
                            //삭제실패
                            postErrorFlag++;
                        }
                    }

                }
                if(postErrorFlag == 0){
                    return_text ="success";
                }else{
                    return_text ="mongoError";
                }

            }


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
