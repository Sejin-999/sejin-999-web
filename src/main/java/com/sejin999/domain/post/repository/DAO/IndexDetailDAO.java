package com.sejin999.domain.post.repository.DAO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Slf4j
public class IndexDetailDAO {
    private Long seq;
    private String title;
    private String content;
    private LocalDateTime LastUpdate; //isUpdate;
    private List<IntroListDAO> introList;
}
