package com.sejin999.domain.post.repository.DTO;

import com.sejin999.domain.post.domain.IntroductionPost;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@Setter
@Slf4j
public class PostDTO {
    private String title;
    private Long introSeq;
    private List<PostDetailDTO> postDetailDTOList;

    public boolean isCreateValid(){
        return isValidLength();
    }

    private boolean isValidLength(){
        return !isEmptyOrNull(title) && title.length() <=30;
    }

    private boolean isEmptyOrNull(String value) {
        return value == null || value.isEmpty();
    }

}
