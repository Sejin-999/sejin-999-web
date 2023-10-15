package com.sejin999.domain.post.repository.DAO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Getter
@Setter
@Slf4j
public class IntroListDAO {
    private Long IntroSeq;
    private String IntroTitle;
    private LocalDateTime LastUpdate;
}
