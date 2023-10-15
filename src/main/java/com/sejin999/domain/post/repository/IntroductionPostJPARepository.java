package com.sejin999.domain.post.repository;

import com.sejin999.domain.post.domain.Index;
import com.sejin999.domain.post.domain.IntroductionPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntroductionPostJPARepository extends JpaRepository<IntroductionPost, Long> {
    List<IntroductionPost> findByIndexEntity(Index index);
    IntroductionPost findBySeq(Long seq);

    boolean existsBySeq(Long seq);
}
