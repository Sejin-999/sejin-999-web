package com.sejin999.domain.user.repository.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

@Getter
@Setter
@Slf4j
public class UserSignUpDTO {
    private String userId;
    private String userPassword;
    private String userNickName;

    public boolean isValied(){
        /**
         * null check & empty check
         * 요구사항 조건 만족 체크
         *
         */
        // 값 정확한지는? mysql 저장할 때 검증하기 때문에 널값인지 체크
        if (userId.isEmpty() || userId == null || userNickName.isEmpty() || userNickName == null || userPassword.isEmpty() || userPassword == null){
            return false;
        }else{
            //원하는 요구사항이 충족되었는가?
            // 정규 표현식: 숫자와 영문자로만 이루어진 문자열
            String regex_idAndNickName = "^[a-zA-Z0-9]+$";
            String regex_password = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).+$";

            // Pattern 객체 생성
            Pattern pattern_idCheck = Pattern.compile(regex_idAndNickName);
            Pattern pattern_passwordCheck = Pattern.compile(regex_password);
            Pattern pattern_nickNameCheck = Pattern.compile(regex_idAndNickName);

            boolean isIdValid = pattern_idCheck.matcher(userId).matches();
            boolean isPasswordValid = pattern_passwordCheck.matcher(userPassword).matches();
            boolean isNickNameValid = pattern_nickNameCheck.matcher(userNickName).matches();

            if (isIdValid && isPasswordValid && isNickNameValid) {
                // 모든 조건이 맞으면 유효
                return true;
            } else {
                // 유효하지 않은 이유
                if (!isIdValid) {
                    log.warn("유효하지 않은 아이디");
                }
                if (!isPasswordValid) {
                    log.warn("유효하지 않은 비밀번호");
                }
                if (!isNickNameValid) {
                    log.warn("유효하지 않은 닉네임");
                }
                return false;
            }

        }
    }
}
