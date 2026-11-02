package org.example.exceptionhandler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @GetMapping
    @RequestMapping(value={"/", "/main"})
    public String main(){
        return "main";
    }

}
