package org.example.fileuproad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    // uui 생성
    /*
        파일
        uuid 생성.


     */

    @RequestMapping({"/", "/main"})
    public String main(){
        return "main";
    }

}


