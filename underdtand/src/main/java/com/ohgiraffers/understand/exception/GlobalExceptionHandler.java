package com.ohgiraffers.understand.exception;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public String nullPointExceptionHandler(NullPointerException e, Model model) {
        System.out.println("global 레벨의 NullPointerException 처리");
        model.addAttribute("message", "빈값이 존재합니다.");
        return "error/errorMessage";
    }

    // DB 제약조건 위반 시 발생 에러
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String SQLIntegrityConstraintViolationExceptionHandler(SQLIntegrityConstraintViolationException e, Model model) {
        model.addAttribute("message", "DB 제약 조건 위반 발생!!");
        return "error/errorMessage";
    }

    @ExceptionHandler(DataAccessException.class)
    public String SQLSyntaxErrorExceptionHandler(SQLSyntaxErrorException e, Model model) {
        model.addAttribute("message", "쿼리 작성문이 잘 못 되었습니다.");
        return "error/errorMessage";
    }

    // 우리가 만든 에러
    @ExceptionHandler(NotInsertNameException.class)
    public String notInsertNameExceptionHandler(NotInsertNameException e, Model model) {
        model.addAttribute("message", "이미 있는 메뉴이거나 공란입니다.");
        return "error/errorMessage";
    }

    // 요청한 페이지를 찾을 수 없을 시
    @ExceptionHandler(NoResourceFoundException.class)
    public String NoResourceFoundExceptionHandler(NoResourceFoundException e, Model model) {
        model.addAttribute("message", "요청하신 페이지를 찾을 수 없습니다.");
        return "error/errorMessage";
    }

//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public String MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e, Model model) {
//        model.addAttribute("message", "카테고리 분류를 알 수 없습니다.");
//        return "error/errorMessage";
//    }

    // 그외 나머지 에러들..
    @ExceptionHandler(Exception.class)
    public String otherExceptionHandler(Exception e, Model model) {
        model.addAttribute("message", "오류가 발생했습니다.");
        return "error/errorMessage";
    }

}
