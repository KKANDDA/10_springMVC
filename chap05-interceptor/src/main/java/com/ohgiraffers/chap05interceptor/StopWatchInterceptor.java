package com.ohgiraffers.chap05interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
* default 메소드가 존재하기 이전에는 모두 오버라이딩을 해야 하는 책임을 가지기 때문에
* JHandlerInterceptorAdaptor를 이용해 사용했으나, default 메소드가 인터페이스에 사용 가능하게 된 java8 번전 이후에는
* 인터페이스만 구현하여 필요한 메소드만 오버라이딩 해서 사용할 수 있다. */
@Component
public class StopWatchInterceptor implements HandlerInterceptor {

    // 전처리 메소드 - 지정된 컨트롤러의 동작 이전에 수행할 내용 // 파라미터를 검증할 때 많이 씀.. // 성능체크할 때도 많이 씀
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 사용자의 파라미터가 request에 담겨 있다.
        if(!request.getParameter("auth").equals("admin")){
            response.sendRedirect("/"); // 루트경로 redirect
            return false; // 요청 처리 중단 // 필터에서 점검가능 하나 여기서도 가능함. 추가로 하면 더 좋지!
        }
        System.out.println("preHandle 호출됨..!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        long startTime = System.currentTimeMillis(); // 요청 처리 시작 시간 기록
        request.setAttribute("startTime", startTime);

        // 컨트롤러를 이어서 호풀한다.
        return true; // 해당 요청이 온 콘트롤로 이동
    }

    // 후처리 메소드 - 지정된 컨트롤러의 동작 이후 처리할 동작 제어
    // 실행이 완료되었지만 아직 view가 생성되기 전에 호출됨..
    // 디스패쳐 서블릿이 화면을 띄우기 전에 동작함..
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("모델 확인용" + modelAndView.getModelMap());
        System.out.println("postHandle 호출함..");
        long startTime = (long)request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        modelAndView.addObject("interval", endTime-startTime);
        // 리턴 없이 스쳐감..
    }

    // 모든 요청 처리가 완료된 후에 실행되는 메소드
    // close 작업들.. 리소스, 세션 정리 등.. 사용이 끝난 데이터 정리..
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion 호출함..");
    }
}
