package com.ohgiraffers.chap04exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice // Exception이 발생했을 때 핸들링 해 주는 클래스를 만드는 어노테이션
// @ExceptionHandler가 우선권이 있고, 거기에서 예외처리가 안 되면 여기로 온다.
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(NullPointerException e){
        System.out.println("Global(서버에서 발생하는 모든) 레벨의 Exception 처리");
        return "error/nullPointer";
    }

    @ExceptionHandler(MemberRegistException.class) // 클래스 이름이 아닌 예외 이름으로 보아야 옳다.
    public String userExceptionHandler(Model model, MemberRegistException e){
        System.out.println("Global 레벨의 exception 처리");
        model.addAttribute("exception", e);
        return "error/memberRegist";
    }

    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception e){
        System.out.println("나머지 exception 발생함");
        return "error/default";
    } // 몇가지 예외처리를 지정해 놓고, 나머지 예외처리는 한꺼번에 처리할 수 있다! 이건 필수네.. 뜻밖의 상황을 대비..

}
