package com.sejin999.domain.user.controller;

import com.sejin999.domain.user.repository.DAO.UserDAO;
import com.sejin999.domain.user.repository.DTO.LoginDTO;
import com.sejin999.domain.user.repository.DTO.UserDTO;
import com.sejin999.domain.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/login")
    public ResponseEntity loginController(@RequestBody LoginDTO loginDTO){
        log.info("login start >> {}",loginDTO.getUserId());
        String return_text = userService.user_login_service(loginDTO);

        return ResponseEntity.ok(return_text);
    }

    @PostMapping("/register")
    public ResponseEntity registerController(@RequestBody UserDTO userSignUpDTO){
        log.info("userSignUpDTO > id: {} password :{} nickName:{}", userSignUpDTO.getUserId(),
                userSignUpDTO.getUserPassword(),
                userSignUpDTO.getUserNickName());

        if(userSignUpDTO.isCreateValid()){
            log.info("userService Start");
            String return_text = userService.user_signUp_service(userSignUpDTO);
            if(return_text.equals("success")){
                return ResponseEntity.ok(return_text);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(return_text);
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("검증기준을 통과하지못하였습니다.");
        }

    }

    @PostMapping("/test_create")
    public ResponseEntity testUserCreateController(@RequestBody UserDTO userSignUpDTO ){
        log.info("testUserCreateController");
        log.info("userSignUpDTO > id: {} password :{} nickName:{}", userSignUpDTO.getUserId(),
                userSignUpDTO.getUserPassword(),
                userSignUpDTO.getUserNickName());

        if(userSignUpDTO.isCreateValid()){
            log.info("userService Start");
            String return_text = userService.user_signUp_service(userSignUpDTO);
            if(return_text.equals("success")){
               return ResponseEntity.ok(return_text);
            }else{
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(return_text);
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("검증기준을 통과하지못하였습니다.");
        }

    }

    @PostMapping("/test_update")
    public ResponseEntity testUserUpdateController(@RequestBody UserDTO userSignUpDTO ){
        log.info("testUserCreateController");
        log.info("userSignUpDTO > id: {} password :{} nickName:{}", userSignUpDTO.getUserId(),
                userSignUpDTO.getUserPassword(),
                userSignUpDTO.getUserNickName());

        if(userSignUpDTO.isCreateValid()){
            log.info("userService Start");
            String return_text = userService.user_update_service(userSignUpDTO);
            if(return_text.equals("User updated success")){
                return ResponseEntity.ok(return_text);
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(return_text);
            }
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("검증기준을 통과하지못하였습니다.");
        }

    }

    @GetMapping("/test_read")
    public ResponseEntity testUserReadController(){
        log.info("testUserReadController >> Start");

        List<UserDAO> userList = userService.user_read_userList_service();

        if (userList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("userData 가 없습니다.");
        }
        return ResponseEntity.ok(userList);
    }

    @DeleteMapping("/test_delete")
    public ResponseEntity testUserDeleteController(@RequestBody String userId){
        log.info("testUserDeleteController >> {}" , userId);

        String return_text = userService.user_delete_service(userId);

        if(return_text.equals("user delete success")){
            return ResponseEntity.ok(return_text);
        }else{
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(return_text);
        }
    }
}
