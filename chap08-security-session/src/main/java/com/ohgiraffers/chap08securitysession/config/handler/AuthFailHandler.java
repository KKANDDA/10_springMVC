package com.ohgiraffers.chap08securitysession.config.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;

// 사용자 로그인 실패시 메세지 담아서 fail로..
// 요청 실패 커스텀 핸들러 // 월래는 인덱스로 갈 요청
@Configuration
public class AuthFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    // 실패시 행동을 정할 메소드

        String errorMessage = null;
        if(exception instanceof BadCredentialsException) {
            // 사용자의 아이디가 DB에 존재하지 않는 경우 or 비밀번호가 맞지 않는 경우
            errorMessage = "아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            // 서버에서 사용자 정보를 검증하는 과정에서 발생하는 에러 -- 대부분 잘 못 된 코드
            errorMessage = "서버에서 오류가 발생되었습니다.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            // 인증정보가 없는 상태에서 보안 처리된 리소스에 접근하는 경우
            errorMessage = "인증 요청이 거부되었습니다.";
        }else{
            errorMessage = "알수 없는 오류로 로그인 요청을 처리할 수 없습니다.";
        }
        errorMessage = URLEncoder.encode(errorMessage, "UTF-8");
        // 요청이 실패했을 시 보낼 곳 지정 - 인코딩 후
        setDefaultFailureUrl("/auth/fail?message=" + errorMessage);

        // 수행 부분만 정의하고 나머지는 super에게 보내서 처리 // 거르지 못한 것들을 시큐리티의 기본 처리로 보냄
        super.onAuthenticationFailure(request, response, exception);
    }
}
