package com.ohgiraffers.chap08securitysession.auth.service;

import com.ohgiraffers.chap08securitysession.auth.model.AuthDetail;
import com.ohgiraffers.chap08securitysession.user.dto.LoginUserDTO;
import com.ohgiraffers.chap08securitysession.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserService userService;



    // 로그인 요청 시 security가, 전달된 사용자의 id(사용자가 입력한 id)를 매개변수로 받아 db에서 사용자의 정보를 찾는다.
    // 서비스를 구현해 주어야 함.
    // security에 전달되는 사용자의 객체 타입은 userDetails를 구현한 구현체가 되어야 한다.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUserDTO loginUserDTO = userService.findByUserNAme(username);

        if(Objects.isNull(loginUserDTO)){
            throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");
        }
        return new AuthDetail(loginUserDTO);
    }

}
