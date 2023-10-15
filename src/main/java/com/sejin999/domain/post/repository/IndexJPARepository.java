package com.sejin999.domain.post.repository;

import com.sejin999.domain.post.domain.Index;
import com.sejin999.domain.post.repository.DAO.IndexDetailDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndexJPARepository extends JpaRepository<Index, Long> {
    List<Index> getAllByIsDELETED(boolean deleted);
    boolean existsBySeq(Long seq);
    Index findBySeq(Long seq);
}
