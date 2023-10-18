package com.sejin999.domain.post.repository.DAO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Slf4j
public class IntroDetailDAO {
    private Long introSeq;
    private String title;
    private String imageURL;
    private String content;
    private LocalDateTime lastUpdateTime;
    private List<PostListDAO> postList;

}
