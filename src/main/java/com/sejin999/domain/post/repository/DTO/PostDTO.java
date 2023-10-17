package com.sejin999.domain.post.repository.DTO;

import com.sejin999.domain.post.domain.IntroductionPost;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class PostDTO {
    private String title;
    private String content;
    private Long introSeq;


    public boolean isCreateValid(){
        return isValidLength();
    }

    private boolean isValidLength(){
        return !isEmptyOrNull(title) && title.length() <=30 &&
                !isEmptyOrNull(content) && content.length() <= 300;
    }

    private boolean isEmptyOrNull(String value) {
        return value == null || value.isEmpty();
    }

}
