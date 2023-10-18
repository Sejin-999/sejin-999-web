package com.sejin999.domain.post.repository;

import com.sejin999.domain.post.domain.IntroductionPost;
import com.sejin999.domain.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostJPARepository extends JpaRepository<Post, Long> {

    boolean existsBySeq(Long postSeq);
    Post findBySeq(Long postSeq);

    List<Post> findByIntroductionPostIndex(IntroductionPost introductionPost);
}
