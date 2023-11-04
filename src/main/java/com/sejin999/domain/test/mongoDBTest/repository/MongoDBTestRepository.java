package com.sejin999.domain.test.mongoDBTest.repository;

import com.sejin999.domain.test.mongoDBTest.domain.Test;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoDBTestRepository extends MongoRepository<Test , Long> {
    Optional<Test> findById(Long id);
}
