package org.example.filterandintercepter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StopwatchController {
//에러 코드 :
    /*
         404 : 경로 문제. 경로를 찾을 수 없는 경우가 대부분.
         500 : 로그 읽으면서 찾아봐야함. 코드 훑어보면서 문제 파악. html 파일에 경로 잘못 적었을 수도 있음.
     */
    @GetMapping("/stopwatch")
    public String stopWatch() throws InterruptedException{
        System.out.println("[4. Controller] handlerMethod 호출");
        Thread.sleep(1000);  //스레드로 바로 끝나지 않게 경과를 줌.
        System.out.println("4. 1초 경과");
        Thread.sleep(2000);
        System.out.println("4. 2초 경과");

        return "result";
    }

}
