package com.sejin999.domain.user.service;

import com.sejin999.domain.user.domain.User;
import com.sejin999.domain.user.repository.UserJPARepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserJWTService {
    private final UserJPARepository userJPARepository;

    public UserJWTService(UserJPARepository userJPARepository) {
        this.userJPARepository = userJPARepository;
    }

    public User user_jwt_data_service(String userId){
        return userJPARepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("사용자 없음"));
    }
}
