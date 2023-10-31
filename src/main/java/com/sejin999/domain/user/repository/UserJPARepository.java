package com.sejin999.domain.user.repository;

import com.sejin999.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Long> {
    boolean existsById(String userId);
    List<User> getUserByIsDELETED(boolean deleted);
    Optional<User> findById(String userId);
}
