package com.sejin999.domain.post.repository.DTO;

import com.sejin999.domain.post.domain.Index;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class IntroDTO {
    private String title;
    private String imageTag;
    private String content;
    private Long indexSeq;

    public boolean isCreateValid(){
        return isValidLength();
    }

    private boolean isValidLength(){
        return !isEmptyOrNull(title) && title.length() <=30 &&
                !isEmptyOrNull(content) && content.length() <= 200;
    }
    private boolean isEmptyOrNull(String value) {
        return value == null || value.isEmpty();
    }

}
