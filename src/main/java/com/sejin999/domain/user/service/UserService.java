package com.sejin999.domain.user.service;

import com.sejin999.domain.user.domain.User;
import com.sejin999.domain.user.repository.DTO.UserSignUpDTO;
import com.sejin999.domain.user.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    private final UserJPARepository userJPARepository;

    public UserService(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    /**
     * CRUD userData
     * create - signUp
     * Read - userData
     * Update - user Data Update
     * Delete - user delete
     */

    //signUp
    public String userSignUpService(UserSignUpDTO userSignUpDTO){
        String return_text = "";

        //user Data 검증 완료..
        String userId = userSignUpDTO.getUserId();
        String userPassword = userSignUpDTO.getUserPassword();
        String userNickName = userSignUpDTO.getUserNickName();


        if(!userJPARepository.existsById(userId)){
            //유저 아이디가 존재하지 않음
            try {
                userJPARepository.save(
                        User.builder()
                                .id(userId)
                                .password(userPassword)
                                .nickName(userNickName)
                                .build()
                );
                return_text = "success";
            }catch (DataIntegrityViolationException e) {
            // 데이터베이스 무결성 제약 조건 위반 - 키 중복
                return_text = "유저 아이디가 이미 존재";
                log.warn("userSignUpService : {}" , return_text);
            } catch (JpaSystemException e) {
            // JPA 연동 중 문제 발생
                return_text = "데이터베이스 연동 중 오류가 발생";
                log.warn("userSignUpService : {}" , return_text);
            } catch (DataAccessException e) {
            // 데이터 액세스 오류
                return_text = "데이터베이스 액세스 중 오류가 발생";
                log.warn("userSignUpService : {}" , return_text);
            } catch (Exception e) {
            // 다른 모든 예외 처리
                return_text = "알 수 없는 오류가 발생";
                log.warn(e.getMessage());
        }


        }else{
            //유저 아이디가 존재
            return_text = "userId exists";

        }

        return return_text;
    }
}
