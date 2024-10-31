package com.ohgiraffers.chap08securitysession.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

    @GetMapping
    public String root(){
        return "index";
    }

    @GetMapping("/admin/page")
    public ModelAndView admin(ModelAndView modelAndView){
        modelAndView.setViewName("admin/admin");
        return modelAndView;
    }

    @GetMapping("user/page")
    public ModelAndView user(ModelAndView modelAndView){
        modelAndView.setViewName("user/user");
        return modelAndView;
    }
}
