package com.ohgiraffers.understand.controller;

import com.ohgiraffers.understand.dto.MenuDTO;
import com.ohgiraffers.understand.exception.NotInsertNameException;
import com.ohgiraffers.understand.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequestMapping("/menus/*")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("menus")
    public ModelAndView slectAllMenu(ModelAndView modelAndView) {
        List<MenuDTO> menuDTOList = menuService.selectAllMenu();
        if(Objects.isNull(menuDTOList)){
            throw new NullPointerException();
        }
        modelAndView.addObject("menuDTOList", menuDTOList);
        modelAndView.setViewName("menus/allMenus");
        return modelAndView;
    }

    @GetMapping("onemenu")
    public ModelAndView oneMenu(ModelAndView modelAndView){
        modelAndView.setViewName("menus/oneMenu");
        return modelAndView;

    }

    @GetMapping("onemenuaction")
    public ModelAndView selectOneMenu(ModelAndView modelAndView, MenuDTO menuDTO/*@RequestParam("code") int code*/){

        // int code = menuDTO.getCode();


        MenuDTO menu = menuService.selectMenuByCode(menuDTO);
        if(Objects.isNull(menu)){
            throw new NullPointerException();
        }
        modelAndView.addObject("menuDTOList", menu);
        // 로직

        modelAndView.setViewName("menus/allMenus");
        return modelAndView;
    }

    @GetMapping("regist")
    public ModelAndView insert(ModelAndView modelAndView){
        modelAndView.setViewName("menus/regist");
        return modelAndView;
    }

    @PostMapping("regist")
    public ModelAndView insertMenu(ModelAndView modelAndView, MenuDTO menuDTO) throws NotInsertNameException {
        System.out.println("test");
        int regist = menuService.regist(menuDTO);
        System.out.println("menuDTO = " + menuDTO);
        System.out.println("regist = " + regist);
        if (regist <= 0){
            modelAndView.addObject("message", "가격은 음수일 수 없습니다.");
            modelAndView.setViewName("/error/errorMessage");
        }else {
            modelAndView.setViewName("/menus/returnMessage");
        }
        return modelAndView;
    }

    @GetMapping("update")
    public ModelAndView update(ModelAndView modelAndView){
        modelAndView.setViewName("menus/update");
        return modelAndView;
    }

    @PostMapping("update")
    //public ModelAndView updateMenu(ModelAndView modelAndView, @RequestParam Map<String, String> parameter /*MenuDTO menuDTO*/){
    public ModelAndView updateMenu(ModelAndView modelAndView,
                                   @RequestParam int code,
                                   @RequestParam(defaultValue = "")String name,
                                   @RequestParam(defaultValue = "0")int price,
                                   @RequestParam(defaultValue = "0") int categoryCode){
        // int update = menuService.update(menuDTO);

        MenuDTO menuDTO = new MenuDTO();
//        int code = Integer.parseInt(parameter.get("code"));
//        String name = parameter.get("name");
//        int price = 0/*Integer.parseInt(parameter.get("price"))*/;
//        int categoryCode = 0/*Integer.parseInt(parameter.get("categoryCode"))*/;
//
//        if(parameter.get("price").isEmpty()){
//            price = 0;
//        } else if (parameter.get("categoryCode").isEmpty()) {
//            categoryCode = 0;
//        }
//
//        if(parameter.get("code").isEmpty()){
//            modelAndView.addObject("message", "수정할 메뉴의 코드는 입력하셔야 합니다.");
//            modelAndView.setViewName("error/errorMessage");
//        }

        menuDTO.setCode(code);
        menuDTO.setName(name);
        menuDTO.setPrice(price);
        menuDTO.setCategoryCode(categoryCode);

        int result = menuService.update(menuDTO);

        if (result <= 0){
            modelAndView.addObject("message", "업데이트 실패");
            modelAndView.setViewName("/error/errorMessage");
        }else {
            modelAndView.setViewName("/menus/returnMessage");
        }
        return modelAndView;
    }

    @GetMapping("delete")
    public ModelAndView delete(ModelAndView modelAndView){
        modelAndView.setViewName("menus/delete");
        return modelAndView;
    }

    @PostMapping("delete")
    public ModelAndView deleteMenu(ModelAndView modelAndView, MenuDTO menuDTO){
        int result = menuService.delete(menuDTO);

        if (result <= 0){
            modelAndView.addObject("message", "삭제 실패");
            modelAndView.setViewName("/error/errorMessage");
        }else {
            modelAndView.setViewName("/menus/returnMessage");
        }
        return modelAndView;
    }

}
