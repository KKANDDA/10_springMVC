package com.ohgiraffers.chap02handlermethod;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@Controller
@RequestMapping("/first/*")
@SessionAttributes("id") // 이 속성을 컨트롤러 내부에서 계속 공유할 수 있게 된다.
public class FirstController {

    @GetMapping("regist")
    public void regist(){}
    // 보이드로 만들면 경로를 찾아간다. 메소드 이름은 전혀 상관이 없고 어노테이션의 이름이 중요하다. /first/regist.html
    // 뷰 리졸보 기능은 @Controller만 가능하다.

    @PostMapping("regist")
    public String registMenu(Model model, WebRequest request){
        String name = request.getParameter("name");
        int price = Integer.parseInt(
                request.getParameter("price"));
        int categoryCode = Integer.parseInt(
                request.getParameter("categoryCode"));

        String message = name + "을(를) 신규 메뉴 목록의 " + categoryCode + "번 카테고리에 " +
                price + "원으로 등록하셨습니다.";

        System.out.println(message);
        model.addAttribute("message", message);
        return "first/messagePrinter";
    }

    @GetMapping("modify")
    public void modify(){} // 페이지 이동

//    // required = 파라미터 포함 여부, name = 이름, defaultValue = 기본 값
//    @PostMapping
//    public String modifyMenu(Model model,
//                             @RequestParam(required = false, name="modifyName") String modifyName,
//                             // 필수 입력이 아니어도 가능하도록 설정, 어노테이션의 이름 설정
//                             @RequestParam(defaultValue = "0", name="modifyPrice") int modifyPrice
//                             // 기본값을 "0"으로 설정,
//    ){
//        String message = modifyName + " 메뉴 가격을 " + modifyPrice + "원으로 변경하였습니다.";
//        System.out.println(message);
//        model.addAttribute("message", message);
//        return "first/messagePrinter";
//
//    }

    @PostMapping("modify")
    public String modifyMenu(Model model,
                             @RequestParam Map<String, String> parameters){
                             // html로 넘어 오는 파라미터는 모두 스트링이다. 그러니 스트링으로 받는다.
        String modifyName = parameters.get("modifyName");
        int modifyPrice = Integer.parseInt(parameters.get("modifyPrice"));

        String message = modifyName + " 메뉴 가격을 " + modifyPrice + "원으로 변경하였습니다.";
        System.out.println(message);
        model.addAttribute("message", message);
        return "first/messagePrinter";
    }

    // 3.
    @GetMapping("search")
    public void search(){}

    @PostMapping("search")
    public String searchMenu(@ModelAttribute("menu")/*바로 담을 거라 객체가 필요 없다.*/ MenuDTO menu){
        System.out.println(menu);
        return "first/searchResult";
    }
    // 4. session 이용하기
    @GetMapping("login")
    public void lofin(){}

    // 4-1. HttpSession을 매개변수로 선언하면 핸들러 메소드 호출 시 세션 객체를 호출함.
    @PostMapping("login")
    public String sessionTest1(HttpSession session, @RequestParam String id){
        session. setAttribute("id", id);
        return "first/loginResult"; // 세션에 담아서 보여주고
    }


    @GetMapping("logout1")
    public String logoutTest1(HttpSession session){
        session.invalidate(); // 지역이든 전역이든 모두 만료
        return "first/login"; // 세션을 닫아서 만료시켰다.
    }

    /*
    * 4-2. SessionAttribute를 이용하여 session에 값 담기
    * 클래스 레벨에 @SessionAttribute 어노테이션을 이용하여 세션에 값을 담을 key를 설정해 두면,
    * model 영역에 해당 key로 값이 추가되는 경우 Session에 자동 등록한다.
    * (@SessionAttribute로 지정된 속성은 해당 컨트롤러 내에서만 유효하다.)*/
    @PostMapping("login2")
    public String sessionTest2(Model model, @RequestParam String id){
        model.addAttribute("id", id);
        // model 객체에 넣었고(session과은 상관없다.),
        // 위에서 지정한 @SessionAttributes("id") 특정 이름은 콘트롤러 안에서 공유된다.
        return "first/loginResult";
    }

    // sessionAttribute로 등록된 값은 session의 상태를 관리하는 sessionStatus의 setComplete() 메소드를 호출해야 만료된다.
    @GetMapping("logout2")
    public String logoutTest2(SessionStatus sessionStatus){
        sessionStatus.setComplete();
        return "first/loginResult";
        // 지역 세션을 만료했는데 @SessionAttributes("id") id를 앞에서 명시해 놔 전역 세션도 만료됐다.
    }

    /*
    5. @RequestBody를 이용하는 방법
    해당 어노테이션은 http 본문 자체를 읽는 부분을 모델로 변화시켜 주는 어노테이션이다.*/
    @GetMapping("body")
    public void body(Model model){}

    @PostMapping("body")
    public void bodyTest(@RequestBody String body) throws UnsupportedEncodingException {
        System.out.println(body);
        System.out.println(URLDecoder.decode(body,"UTF-8"));
        // 리액트에서 패치 요청을 제이슨으로 받을 때 많이 사용한다.
        // 리액트에서 바디를 까기만 하다가 리액트에서 바디에 담아서 보낸 것을 깔 때 사용한다.
    }
}
