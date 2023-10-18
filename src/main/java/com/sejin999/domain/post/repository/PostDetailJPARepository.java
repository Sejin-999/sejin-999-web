package com.sejin999.domain.post.repository;

import com.sejin999.domain.post.domain.Post;
import com.sejin999.domain.post.domain.PostDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostDetailJPARepository extends JpaRepository<PostDetail, Long> {
    List<PostDetail> findByPost(Post post);

}
