package com.sejin999.domain.post.repository.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class IntroUpdateDTO {
    private Long introSeq;
    private IntroDTO introDTO;
}
