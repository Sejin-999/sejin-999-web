package com.sejin999.domain.comment.repostiory;

import com.sejin999.domain.comment.domain.MasterComment;
import com.sejin999.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MasterCommentJPARepository extends JpaRepository<MasterComment, Long> {
    MasterComment findBySeq(Long masterCommentSeq);
    boolean existsBySeq(Long masterCommentSeq);
}
