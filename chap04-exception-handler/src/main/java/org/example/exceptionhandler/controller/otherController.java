package org.example.exceptionhandler.controller;

import org.example.exceptionhandler.exception.MemberRegistException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class otherController {

    @GetMapping("/other-controller-null")
    public String otherControllerException(){
        String str = null;
        System.out.println(str.charAt(0));
        return "/";
    }

    @GetMapping("/other-controller-user")
    public String userException() throws MemberRegistException { //던지려고 하는 예외 클래스 설정. dispatch로 던짐??
        boolean check = true;
        if (check) {

            throw new MemberRegistException("당신은 우리서비스의 회원이 될 수 없습니다");
        }
        return "/"; //main으로 return
    }

}
