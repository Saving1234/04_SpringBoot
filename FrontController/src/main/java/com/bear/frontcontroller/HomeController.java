package com.bear.frontcontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class HomeController {

    //request와 response를 매개변수로 받는 일반 클래스.
    public String getIndex(HttpServletRequest req, HttpServletResponse resp) {
        return "index";
    }
}
