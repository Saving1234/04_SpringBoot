package org.example.requesmapping.chap01;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//처리 위임하는 컨트롤러.
@Controller
public class MethodMappingTestController {

    //RequestMapping URL만 작성할 경우.
    //모든 HTTP 메소드(GET, POST, DELETE, PUT)이 하나의 핸들러메소드로 동작함.
    //핸들러 매소드 지정. menu/regist로 요청오면 이거 호출??
    @RequestMapping("/menu/regist")
    public String registMenu(Model model){ //스프링 프레임워크 모델.

        model.addAttribute("message", "신규 등록된 핸들러메소드 호출함");
        return "mappingResult";
    }

    //요청 메소드 get요청으로 지정.
//    @RequestMapping(value = "/menu/modify", method = RequestMethod.GET)

    @GetMapping("/menu/modify")
    public String modifyMenu(Model model){ //스프링 프레임워크 모델.

        model.addAttribute("message", "get 신규 등록된 핸들러메소드 호출함");
        return "mappingResult";
    }

    //요청전용 어노테이션. - 해당 url 메서드에 대해서만 처리할 수 있도록.
    /*
        @GetMapping -
        @PostMapping
        @PutMapping
        @DeleteMapping
        @PatchMappingMapping
     */


}
