package com.sejin999.domain.test.mongoDBTest.contoller;

import com.sejin999.domain.test.mongoDBTest.domain.Test;
import com.sejin999.domain.test.mongoDBTest.service.MongoDBTestService;
import com.sejin999.domain.user.repository.DTO.LoginDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/test")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MongoDBTestController {
    private final MongoDBTestService mongoDBTestService;

    public MongoDBTestController(MongoDBTestService mongoDBTestService) {
        this.mongoDBTestService = mongoDBTestService;
    }

    @PostMapping("/mongo/create")
    public ResponseEntity createController(@RequestBody Test test){
        return ResponseEntity.ok(mongoDBTestService.createTest(test));
    }

    @GetMapping("/mongo/read/{testNum}")
    public ResponseEntity readController(@PathVariable Long testNum){
        return ResponseEntity.ok(mongoDBTestService.readTest(testNum));
    }
}
