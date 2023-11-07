package com.sejin999.domain.Image.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "tempImgData")
@Getter
@Setter
public class ImageData {
    @Id
    private int randomNumber;
    private String imageURL;
}
