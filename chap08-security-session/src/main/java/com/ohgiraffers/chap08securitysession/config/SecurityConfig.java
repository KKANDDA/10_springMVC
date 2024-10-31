package com.ohgiraffers.chap08securitysession.config;

import com.ohgiraffers.chap08securitysession.common.UserRole;
import com.ohgiraffers.chap08securitysession.config.handler.AuthFailHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.security.Security;

@Configuration
@EnableWebSecurity // 시큐리티의 리소스들 (빈들을) 관리하는 곳..
public class SecurityConfig {

    @Autowired
    private AuthFailHandler authFailHandler;

    // 비밀번호 인코딩 Bean

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 암호화.. 원본과 비교만 가능..
    }

    // 정적 리소스 요청 제외 Bean // 이미지 같은 것들..
    public WebSecurityCustomizer webSecurityCustomizer() {

        return web -> // 람다 문법.. 화살표 함수..
                web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // 필터 체인 커스텀
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> { // 서버의 리소스에 접근 가능한 권한 설정 // auth가 요청을 담고 있다.
            auth.requestMatchers("/auth/login", "/user/signup", "/auth/fail","/").permitAll();

            // Role_admin에게만 권한을 허용하겠다.
            auth.requestMatchers("/admin/*").hasAnyAuthority("ADMIN");

            // Role_user에게만 권한을 허용하겠다.
            auth.requestMatchers("/user/*").hasAnyAuthority(UserRole.USER.getRole());
            auth.anyRequest().authenticated(); // 모든 요청을 인증된 사용자에게 허용해 주겠다.
        }).formLogin(login ->{
            login.loginPage("/auth/login"); // 여기로 포스트 요청이 날라오면, 시큐리티가 처리해 주겠다. 겟요청이 있어야하고, 해당하는 페이지와 서블릿이 존재해야 한다.
            login.usernameParameter("user");
            login.passwordParameter("pass"); // html input name의 값
            login.defaultSuccessUrl("/"); // 로그인 성공 시 보낼 곳 설정.. mapping이 존재해야 한다.
            login.failureHandler(authFailHandler); // 실패 시 처리
        }).logout(logout ->{
            // 로그 아웃 시 요청을 날릴 url 설정 // 페이지 만들 필요 없음.
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"));
            // 사용자가 세션을 쓰지 못하게 제거
            logout.deleteCookies("JSESSIONID"); // 세션과 연결된 쿠키 // 사용자 pc
            logout.invalidateHttpSession(true); // 세션이 소멸하는 걸 허용하는 메소드 // 서버
            logout.logoutSuccessUrl("/"); // 로그아웃 완료 후 이동할 페이지 설정
        }).sessionManagement(session ->{
            session.maximumSessions(1); // 세션의 갯수 제한 1로 설정 시 중복 로그인 X
            session.invalidSessionUrl("/"); // 세션 만료시 이동할 페이지 // 세션 시간 만료시..
        }).csrf(csrf ->csrf.disable()); // csrf 처리 안 함. // 교육상 공격 허용..

        return http.build();
    }



}
