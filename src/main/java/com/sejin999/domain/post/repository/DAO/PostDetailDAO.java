package com.sejin999.domain.post.repository.DAO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Slf4j
public class PostDetailDAO {
    private String title;
    private Long postSeq;
    private LocalDateTime lastUpdateTime;
    private LocalDateTime createTime;
    private List<PostDetailListDAO> postDetailListDAO;

}
