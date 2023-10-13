package com.sejin999.domain.post.repository.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class IndexDTO {
    private String title;
    private String content;

    public boolean isCreateValid(){
        return isValidLength();
    }
    private boolean isValidLength() {
        return !isEmptyOrNull(title) && title.length() <= 10 &&
                !isEmptyOrNull(content) && content.length() <= 10;
    }
    private boolean isEmptyOrNull(String value) {
        return value == null || value.isEmpty();
    }

}
