package com.ohgiraffers.chap01requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller // 내부로직은 스프링이 해주고 무조건 처음 받는 어노테이션
public class MethodMappingTestController {

    @RequestMapping("/menu/regist")
    public String registMenu(Model model) {
        model.addAttribute("message", "신규 메뉴 등록용 핸들러 메소드 호출");

        return "mappingResult";
    }

    @RequestMapping(value = "/menu/modify", method = RequestMethod.GET) // GET 부분을 수정하면..
    public String modifyMenu(Model model) {
        model.addAttribute("message", "Get 방식의 메뉴 수정 호출");
        return "mappingResult";
    }

    /*
    요청 메소드 전용 어노테이션
    요청 메소드          어노테이션
    post               @PostMapping
    get                @GetMapping
    Put                @PutMapping // 기존에 있는 내용을 대체, 전체 수정
    Delete             @DeleteMapping // 삭제
    Patch              @PatchMapping // 기존에 있는 내용을 유지, 부분 수정
    * */

    @GetMapping("/menu/delete")
    public String getDeleteMenu(Model model) {
        model.addAttribute("message", "Get 방식의 삭제 핸들러 메소드 호출");
        return "mappingResult";
    }

    @PostMapping("/menu/delete")
    public String postDeleteMenu(Model model) {
        model.addAttribute("message", "Post 방식의 삭제 핸들러 메소드 호출");
        return "mappingResult";
    }
}
