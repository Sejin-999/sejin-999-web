package com.sejin999.domain.test.mongoDBTest.service;

import com.sejin999.domain.test.mongoDBTest.domain.Test;
import com.sejin999.domain.test.mongoDBTest.repository.MongoDBTestRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MongoDBTestService {
    private final MongoDBTestRepository mongoDBTestRepository;

    public MongoDBTestService(MongoDBTestRepository mongoDBTestRepository) {
        this.mongoDBTestRepository = mongoDBTestRepository;
    }
    public String createTest(Test test) {
        try {
            mongoDBTestRepository.save(test);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "fail";
        }
    }
    public Optional<Test> readTest(Long testNum){
        return mongoDBTestRepository.findById(testNum);
    }
}
