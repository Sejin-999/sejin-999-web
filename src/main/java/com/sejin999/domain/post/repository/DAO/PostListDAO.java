package com.sejin999.domain.post.repository.DAO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostListDAO {
    private Long postSeq;
    private String title;
    private LocalDateTime lastUpdateTime;
}
