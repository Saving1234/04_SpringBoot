package org.example.exceptionhandler.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//여러 controller에서 발생한 예외를 공통 처리할 수 있는.
//즉, controller 전용 예외 클래스\
//또한 모든 컨트롤러에 적용되는 공통 코드를 모아놓는 공간.
@ControllerAdvice  // 분리시켜 관리할 수 있다.
public class GlobalExceptionHandler{

    @ExceptionHandler(NullPointerException.class)   //exception. 부모격인 것을 만들어서 처리.
    public String nullPointExceptionHandler(NullPointerException exception){

        System.out.println("Global 레벨의 exception 처리");


        return "error/nullPointer";
    }

    @ExceptionHandler(MemberRegistException.class) //model이 화면에 띄워주는 것인데, 안넣어서 메시지.
    public String memberRegistHandler(Model model,MemberRegistException exception){

        System.out.println("memberRegistEXCEPTION 레벨의 모든 exception 처리");
        model.addAttribute("exception", exception);

        return "error/memberRegist";
    }

    //exception은 대부분의 일반 예외가 상속하는 타입.
    // 구체적인 처리 메소드가 없을 경우에는 마지막 안정망으로 사용.
    //spring에서는 발생한 예외와 가장 구체적으로 일치하는 처리 메서드를 우선 사용
    //특정경로에 404가 나오는 것처럼, 값이 없으면 여기에서 처리???
    @ExceptionHandler(Exception.class)
    public String exception(Exception exception){

        return "error/default";
    }



}
