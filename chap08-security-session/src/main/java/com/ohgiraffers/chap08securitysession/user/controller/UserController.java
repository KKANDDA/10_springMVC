package com.ohgiraffers.chap08securitysession.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user/*")
public class UserController {

    @GetMapping("/signup")
    public ModelAndView signup(ModelAndView modelAndView) {
        modelAndView.setViewName("user/signup");
        return modelAndView;
    }

}
