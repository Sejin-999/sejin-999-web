package com.sejin999.domain.Image.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("temp_img_data")
@Getter
@Setter
public class ImageData {
    @Id
    private String randomTag;
    private String imageURL;
}
