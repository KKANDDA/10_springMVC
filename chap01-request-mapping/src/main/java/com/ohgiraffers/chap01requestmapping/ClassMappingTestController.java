package com.ohgiraffers.chap01requestmapping;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/order/*") // 오더 하위는 이 클래스에서 처리하겠당~!! 성능의 이점보단 패키지 관리에 수월하기에 사용한다.
public class ClassMappingTestController {

    @GetMapping("/regist")
    public String registOrder(Model model) {
        model.addAttribute("message", "Get 방식의 주문 등록용 핸들러 메소드 호출");
        return "mappingResult"; // @@Controller 어노테이션이 있을 때만 매핑해서 html을 찾아 준다.
    }

    @RequestMapping(value = "modify", method = RequestMethod.POST) // 여러개를 요청을 한 번에 받고 싶을 때 사용
    public String modifyOrder(Model model) {
        model.addAttribute("message", "post 방식의 주문 정보 수정 햄들러 메소드 호출");
        return "mappingResult";
    }

    // path 베리어블 /order/1  // 쿼리스팅 파라메터 /order?name=서현준

    /*
    * PathVariable
    * @PathVariable 어노테이션을 이용해 변수를 받아올 수 있다.
    * path variable로 전달되는 {변수명}은 반드시 매개변수명과 동일해야 한다.
    * 만약 동일하지 않으면 @PathVariable("이름"을 설정 해 주성야 한다.
    * */
    @GetMapping("/detail/{orderNo}") // path의 변수명과 어노테이션의 이름과 현재 사용할 변수명을 일치 시키는 게 안전하다.
    public String selectOrderDetail(Model model, @PathVariable("orderNo") int orderNo) {
        model.addAttribute("message", orderNo+"번 주문 " +
                "상세 내용 조회용 핸들러 메소드 호출");
        return "mappingResult";
    }

    @RequestMapping  // 매핑되는 값을 지정하지 않으면 error 페이지 처럼 사용할 수 있다.
    public String otherRequest(Model model) {
        model.addAttribute("message", "order 요청이긴 하지만 다른 기능이 준비되지 않음");
        return "mappingResult";
    }



}
