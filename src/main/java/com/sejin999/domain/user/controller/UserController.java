package com.sejin999.domain.user.controller;

import com.sejin999.domain.user.repository.DTO.UserSignUpDTO;
import com.sejin999.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity testUserController(@RequestBody UserSignUpDTO userSignUpDTO ){
        log.info("testUserController -----  test");
        log.info("userSignUpDTO > id: {} password :{} nickName:{}", userSignUpDTO.getUserId(),
                userSignUpDTO.getUserPassword(),
                userSignUpDTO.getUserNickName());

        if(userSignUpDTO.isValied()){
            log.info("userService Start");
        }else{

        }

    }
}
