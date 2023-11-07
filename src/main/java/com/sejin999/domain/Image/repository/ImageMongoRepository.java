package com.sejin999.domain.Image.repository;

import com.sejin999.domain.Image.domain.ImageData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageMongoRepository extends MongoRepository<ImageData , Long> {
    boolean existsByRandomNumber(Long randomNumber);
}
