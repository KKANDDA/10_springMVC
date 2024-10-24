package com.ohgiraffers.chap05interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 이것도 빈이지만 이 안의 빈들을 설정할 수도 있다.
public class WebConfiguration implements WebMvcConfigurer { // 설정 추가..
             // spring의 been 설정 클래스  // spring mvc 설정 추가 용도
    @Autowired
    private  StopWatchInterceptor stopWatchInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 인터셉터 등록
        registry.addInterceptor(stopWatchInterceptor)
                // /stopwatch 경로에 등록한 인터셉터 적용
                .addPathPatterns("/stopwatch")
        // stopWatchInterceptor 추가하고 /stopwatch 경로에서만 사용할 거야
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/images/**")
                .excludePathPatterns("/js/**"); // 정적 스태틱 리소스들은 뺀다.

    }
}
