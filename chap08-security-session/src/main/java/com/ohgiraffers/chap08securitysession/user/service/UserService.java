package com.ohgiraffers.chap08securitysession.user.service;

import com.ohgiraffers.chap08securitysession.user.dao.UserMapper;
import com.ohgiraffers.chap08securitysession.user.dto.LoginUserDTO;
import com.ohgiraffers.chap08securitysession.user.dto.SignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public PasswordEncoder encoder; // 비크립트가 들어온다..

    // Transactional - 메소드가 정상적으로 완료되면 커밋함. 실행 중 예외 발생 시 롤백함.
    @Transactional
    // 비크립트 시킬 빈이 필요하다..
    public int regist(SignupDTO signupDTO) {
        if (signupDTO.getUserId() == null || signupDTO.getUserId().isEmpty()){
            return 0;
        }
        if(signupDTO.getUserName() == null || signupDTO.getUserName().isEmpty()){
            return 0;
        }
        if (signupDTO.getUserPass() == null || signupDTO.getUserPass().isEmpty()){
            return 0;
        }
        signupDTO.setUserPass(encoder.encode(signupDTO.getUserPass())); // 받은 원본을 암호화해서 넣었다.
        int result = userMapper.regist(signupDTO);
        return result;
    }


    public LoginUserDTO findByUserNAme(String username) {

        LoginUserDTO loginUserDTO = userMapper.findByUserName(username); // name이 네임이 아니다. 식별자, 고유한 것.
        if(Objects.isNull(loginUserDTO)){
            return null;
        }else {
            return loginUserDTO;
        }
    }
}
