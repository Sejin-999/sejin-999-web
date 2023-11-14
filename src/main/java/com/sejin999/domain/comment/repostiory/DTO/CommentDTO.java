package com.sejin999.domain.comment.repostiory.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class CommentDTO {
    private String comment;
    private String userId;
    private Long postSeq;
    private Long masterCommentSeq;
    
    public boolean isCreateValid(String type){
        /**
         * commnet 200글자 이하
         * userId, postSeq 반드시 필요 
         * 만약 type이 SLAVE인 경우 -> masetCommentSeq 필요
         */
        if(type.equals("SLAVE")){
            //대댓글 검사
            return isValidDefaultLength()&&isValidSlave();
        }else{
            //댓글 검사
            return isValidDefaultLength();
        }
    }
    private boolean isValidDefaultLength(){
        return !isEmptyOrNull(comment) && comment.length() <=200
                && !isEmptyOrNull(userId) && postSeq !=null;

    }
    private boolean isValidSlave(){
        return masterCommentSeq != null;
    }

    private boolean isEmptyOrNull(String value) {
        return value == null || value.isEmpty();
    }

}
