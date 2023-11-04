package com.sejin999.domain.global.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

@Component
public class Base64ToImageUtil {
    public byte[] decodeBase64ToBytes(String base64Image) {
        String cleanedBase64Image = base64Image.replaceAll("[^a-zA-Z0-9+/=]", "");

        // 정리된 Base64 문자열을 디코딩
        return Base64.getDecoder().decode(cleanedBase64Image);
    }

    public void saveImageFromBytes(byte[] imageBytes, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
