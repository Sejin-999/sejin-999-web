package com.sejin999.domain.comment.repostiory;

import com.sejin999.domain.comment.domain.SlaveComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlaveCommentJPARepository  extends JpaRepository<SlaveComment, Long> {
}
