package com.ohgiraffers.chap10websocketreact.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 요철에 대해
                .allowedOrigins("http://localhost:3000") // 허용할 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메소드
                .allowedHeaders("*"); // 허용할 헤더 // 겟은 헤더에 담아서 보내고, 포스트는 바디에 담아서 보내고..
    }
}
