package com.ohgiraffers.chap08securitysession.auth.model;

import com.ohgiraffers.chap08securitysession.user.dto.LoginUserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AuthDetail implements UserDetails { // 이하를 구현해야 시큐리티가 일을 한다.

    private LoginUserDTO loginUserDTO;

    public AuthDetail() {
    }

    public AuthDetail(LoginUserDTO loginUserDTO) {
        this.loginUserDTO = loginUserDTO;
    }

    public LoginUserDTO getLoginUserDTO() {
        return loginUserDTO;
    }

    public void setLoginUserDTO(LoginUserDTO loginUserDTO) {
        this.loginUserDTO = loginUserDTO;
    }

    // 구현한 정보 반환 메소드 확인
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String role: loginUserDTO.getRole()){
            authorities.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return role;
                }
            }); // 시큐리티에서 원하는 자료형으로 바꿔주는 작업
        }
        return authorities;
    }

    // 사용자의 비밀번호를 반환하는 메소드
    @Override
    public String getPassword() {
        return loginUserDTO.getPassword();
    }

    // 사용자의 아이디를 반환하는 메소드
    @Override
    public String getUsername() {
        return loginUserDTO.getUserId(); // 고유 식별자 확인..
    } // 시큐리티가 입력한 정보와 db에 저장된 정보를 비교하기 위한..

    // 계정 만료 여부를 표현하는 메소드 - false이면 해당 계정을 사용할 수 없다.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 잠겨있는 계정을 확인하는 메소드 - false면 사용할 수 없다.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 탈퇴 계정 여부 표현 메소드 - false면 사용할 수 없다.
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 삭제해도 db에 바로 지우지 않고 보관한다. 여기서 막는다.
    }

    // 계정 비활성화로 사용자가 사용 못하는 경우
    @Override
    public boolean isEnabled() {
        return true; // 인스타 계정 비활성화..
    }
}
