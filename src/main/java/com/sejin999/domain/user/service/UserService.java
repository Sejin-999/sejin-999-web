package com.sejin999.domain.user.service;

import com.sejin999.domain.user.domain.User;
import com.sejin999.domain.user.repository.DTO.UserDTO;
import com.sejin999.domain.user.repository.UserJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
     * Update - user Data Update -> password , nickName
     * Delete - user delete
     */

    //signUp
    public String userSignUpService(UserDTO userSignUpDTO){
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
            // 데이터베이스 무결성 제약 조건 위반 - 키 중복  or 조건 위배
                return_text = "사용자의 데이터 제대로 검증되지 않았습니다.";
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


    public String userUpdateService(UserDTO userDTO){
        /**
         * userId 존재하는지 확인 -> 
         * 존재한다면 데이터 업데이트 -> 앞에서 데이터는 체크함
         */
        log.info("userUpdateService start >> {}" , userDTO.getUserId());
        String return_text = "";
        // userId 값으로 가지고올것 -> String 이기 때문에 Optional 로 불러서 체크를 일단해보고 .. 존재여부를 파악해야지
        Optional<User> user = Optional.ofNullable(userJPARepository.findById(userDTO.getUserId()));

        if (user.isPresent()) {
            User userToUpdate = user.get();
            userToUpdate.setPassword(userDTO.getUserPassword());
            userToUpdate.setNickName(userDTO.getUserNickName());

            // 업데이트된 사용자 정보 저장
            try{
                log.info("userUpdateService >> update Start to DB >> {}",userDTO.getUserId());
                userJPARepository.save(userToUpdate);

            }catch (DataIntegrityViolationException e) {
                // 데이터베이스 무결성 제약 조건 위반 - 키 중복  or 조건 위배
                return_text = "사용자의 데이터 제대로 검증되지 않았습니다.";
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

            return_text =  "User updated success";
        }else{
            return_text = "User not exist";
        }

        return return_text;
    }

}
