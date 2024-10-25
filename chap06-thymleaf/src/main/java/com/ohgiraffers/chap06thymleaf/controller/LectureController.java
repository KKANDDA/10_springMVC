package com.ohgiraffers.chap06thymleaf.controller;

import com.ohgiraffers.chap06thymleaf.model.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/lecture/*")
public class LectureController {

    @GetMapping("expression")
    public ModelAndView expression(ModelAndView mv){

        mv.addObject("member", new MemberDTO("서현준", 37, '남', "서울시 동작구"));
        mv.addObject("hello", "hello <h3>Thymeleaf</h3>");

        mv.setViewName("/lecture/expression");

        return mv;
    }

    @GetMapping("conditional")
    public ModelAndView conditional(ModelAndView mv){

        mv.addObject("num", 1);
        mv.addObject("str", "바나나");

        List<MemberDTO> memberList = new ArrayList<>();
        memberList.add(new MemberDTO("서양일", 71, '남', "서울시 동작구"));
        memberList.add(new MemberDTO("서현준", 37, '남', "서울시 동작구"));
        memberList.add(new MemberDTO("서현욱", 36, '남', "서울시 동작구"));
        memberList.add(new MemberDTO("서현오", 31, '남', "서울시 동작구"));
        memberList.add(new MemberDTO("임영환", 30, '남', "서울시 동작구"));

        mv.addObject("memberList", memberList);

        mv.setViewName("/lecture/conditional");
        return mv;
    }

    @GetMapping("fragment")
    public ModelAndView fragment(ModelAndView mv){
        mv.addObject("test1", "value1");
        mv.addObject("test2", "value2");
        mv.setViewName("lecture/fragment");
        return mv;
    }

}
