package org.example.exceptionhandler.controller;

import org.example.exceptionhandler.exception.MemberRegistException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

//ExceptionHandler는 해당 클래스 내에서만 exception 잡음.

@Controller
public class ExceptionHandlerController {

    @GetMapping("/controller-null")
    public String nullPointException() {

        String str = null;
        System.out.println(str.charAt(0)); //문자열 없는데 꺼내라고 하니까, nullPointException 발생.

        return "/";
    }

    /*
        @ExceptionHandler
        스프링 부트에서 컨트롤러 요청 처리 중 발생한 예외를 처리하는 메서드(handler)에 사용하는 어노테이션.
        지정한 예외가 발생하면 해당 예외처리메소드(handler)가 실행.
        Exception이라고 하게 되면, 부모격이기에 다른 exception도 여기서 처리됨.
     */
    @GetMapping("/controller-user")
    public String userException() throws MemberRegistException { //던지려고 하는 예외 클래스 설정. dispatch로 던짐??
        boolean check = true;
        if (check) {
            throw new MemberRegistException("당신은 우리서비스의 회원이 될 수 없습니다");
        }
        return "/"; //main으로 return
    }

    @ExceptionHandler(NullPointerException.class)
    //nullPonintException 잡으려고 만든거니까, nullpointException.class 넣기.
    //class자체로 exception잡겠다는 말인지?
    public String nullPointExceptionHandler(NullPointerException exception) {

        System.out.println("contrller 레벨의 exceptioni 처리");

        return "error/nullPointer";
    }

    @ExceptionHandler(MemberRegistException.class)   //exception. 부모격인 것을 만들어서 처리.
    //nullPonintException 잡으려고 만든거니까, nullpointException.class 넣기.
    //class자체로 exception잡겠다는 말인지?
    public String nullPointExceptionHandler(Model model, MemberRegistException exception){

        System.out.println("contrller 레벨의 exceptioni 처리");
        model.addAttribute("exception", exception);

        return "error/memberRegist";
    }

}




