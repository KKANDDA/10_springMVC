package com.ohgiraffers.chap08securitysession.user.dto;

import com.ohgiraffers.chap08securitysession.common.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginUserDTO {
    private int userCode;
    private String userId;
    private String userName;
    private String password;
    private UserRole userRole;

    public LoginUserDTO() {
    }

    public LoginUserDTO(int userCode, String userId, String userName, String password, UserRole userRole) {
        this.userCode = userCode;
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
    }

    public int getuserCode() {
        return userCode;
    }

    public void setuserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    // 유저의 권한이 보통 여러 개 가지고 있기 때문에 시큐리티가 리스트를 요구한다.
    public List<String> getRole(){
        if(this.userRole.getRole().length() > 0){
            return Arrays.asList(this.userRole.getRole().split(","));
        }
        return new ArrayList<>();
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "LoginUserDTO{" +
                "userCode=" + userCode +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
