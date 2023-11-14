package com.sejin999.domain.comment.service;

import com.sejin999.domain.comment.domain.CommentType;
import com.sejin999.domain.comment.domain.MasterComment;
import com.sejin999.domain.comment.domain.SlaveComment;
import com.sejin999.domain.comment.repostiory.DTO.CommentDTO;
import com.sejin999.domain.comment.repostiory.MasterCommentJPARepository;
import com.sejin999.domain.comment.repostiory.SlaveCommentJPARepository;
import com.sejin999.domain.post.domain.Post;
import com.sejin999.domain.post.service.PostService;
import com.sejin999.domain.user.domain.User;
import com.sejin999.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final MasterCommentJPARepository masterCommentJPARepository;
    private final SlaveCommentJPARepository slaveCommentJPARepository;

    private final UserService userService;
    private final PostService postService;
    public AtomicReference<String> comment_upload_Service(CommentDTO commentDTO , String commentType){
        log.info("comment_upload_service_start");

        AtomicReference<String> return_text = new AtomicReference<>("");
        /**
         * 유저정보 확인.. 과정
         */
        String userId = commentDTO.getUserId();
        Optional<User> userOptional = userService.user_Finder_for_id_Service(userId);

        userOptional.ifPresent(user -> {
            /**
             * post Setting start
             */

            Long postSeq = commentDTO.getPostSeq();
            Post post = postService.post_finder_for_seq_service(postSeq);

            if(post != null){
                //post 존재

                if (CommentType.MASTER.name().equals(commentType)) {
                    try {
                        masterCommentJPARepository.save(
                                MasterComment.builder()
                                        .comment(commentDTO.getComment())
                                        .user(user)
                                        .post(post)
                                        .build()
                        );
                        log.info("Comment -> masterComment 등록완료 \nuserId : {}",user.getId() );
                        return_text.set("Master comment uploaded.");

                    }catch (Exception e){
                        log.warn("master Comment -> db error");
                        return_text.set("db access error");
                    }
                }else if (CommentType.SLAVE.name().equals(commentType)) {
                    // SLAVE에 대한 처리
                    /**
                     * Master comment setting start
                     */
                    MasterComment masterComment = find_MasterComment(commentDTO.getMasterCommentSeq());

                    if(masterComment != null){
                        //master Comment 존재
                        try {
                            slaveCommentJPARepository.save(
                                    SlaveComment
                                            .builder()
                                            .comment(commentDTO.getComment())
                                            .post(post)
                                            .user(user)
                                            .masterComment(masterComment)
                                            .build()
                            );
                            log.info("Comment -> slaveComment 등록완료 \nuserId : {}",user.getId() );
                            return_text.set("Slave comment uploaded.");

                        }catch (Exception e){
                            log.warn("master Comment -> db error");
                            return_text.set("db access error");
                        }


                    }else{
                        //master comment 없음
                        log.warn("masterComment 없음 ... 확인");
                        return_text.set("Master comment is not exists");
                    }

                } else {
                    // 그 외의 경우 처리
                    log.warn("comment type error - front 확인바람");
                    return_text.set("Invalid comment type.");
                }
            }else{
                //post 없음
                log.warn("post 없음");
                return_text.set("Post is not exists");
            }
           
        });

        return  return_text;

    }

    private MasterComment find_MasterComment(Long masterCommentSeq){
        if(masterCommentJPARepository.existsBySeq(masterCommentSeq)){
            //master Comment 존재
            return masterCommentJPARepository.findBySeq(masterCommentSeq);
            
        }else{
            //master Comment 없음
            return  null;
        }
    }

}
