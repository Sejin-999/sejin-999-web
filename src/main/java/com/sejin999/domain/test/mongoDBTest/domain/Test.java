package com.sejin999.domain.test.mongoDBTest.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "test")
@Getter
@Setter
public class Test {
    @Id
    private Long id;
    private String name;
}
