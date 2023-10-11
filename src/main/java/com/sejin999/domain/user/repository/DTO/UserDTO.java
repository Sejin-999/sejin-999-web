package com.sejin999.domain.user.repository.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Getter
@Setter
@Slf4j
public class UserDTO {
    private String userId;
    private String userPassword;
    private String userNickName;
    public boolean isCreateValid() {
        /**
         * null check & empty check
         * 요구사항 조건 만족 체크
         * id >= 30 , 영어 , 숫자만 허용
         * password >200 영어 , 숫자 , 특수기호
         * nickName >= 10 , 영어 ,숫자만 허용
         */
        // 값 정확한지는? mysql 저장할 때 검증하기 때문에 널값인지 체크
        // isBlank도 존재 .. 아직 뭐가 더 좋은지는 잘모르겠음
        // isEmpty -> 문자열 길이가 0인지 확인하는함수
        // isBlank -> 공백인 경우 -> true

        return isValidLength() && isValidCharacters() && meetsRequirements();
    }

    private boolean isValidLength() {
        return !isEmptyOrNull(userId) && userId.length() <= 30 &&
                !isEmptyOrNull(userNickName) && userNickName.length() <= 10 &&
                !isEmptyOrNull(userPassword) && userPassword.length() <= 200;
    }

    private boolean isValidCharacters() {
        return isValidPattern(userId, "^[a-zA-Z0-9]+$") &&
                isValidPattern(userNickName, "^[a-zA-Z0-9]+$") &&
                isValidPattern(userPassword, "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).+$");
    }

    private boolean meetsRequirements() {
        if (!isValidPattern(userId, "^[a-zA-Z0-9]+$")) {
            log.warn("유효하지 않은 아이디");
            return false;
        }
        if (!isValidPattern(userPassword, "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).+$")) {
            log.warn("유효하지 않은 비밀번호");
            return false;
        }
        if (!isValidPattern(userNickName, "^[a-zA-Z0-9]+$")) {
            log.warn("유효하지 않은 닉네임");
            return false;
        }
        return true;
    }

    private boolean isValidPattern(String value, String pattern) {
        Pattern compiledPattern = Pattern.compile(pattern);
        return compiledPattern.matcher(value).matches();
    }

    private boolean isEmptyOrNull(String value) {
        return value == null || value.isEmpty();
    }

}
