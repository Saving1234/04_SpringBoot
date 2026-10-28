package com.bear.frontcontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.List;

//Todo 와 관련된 요청 처리.
public class TodoController {

    public String getList(HttpServletRequest req, HttpServletResponse resp){

        List<String> todoList = Arrays.asList("Todo 1", "TODO 2", "Todo3");
        req.setAttribute("todoList", todoList);

        return "todo/list"; // forward인데 주소만 반환??? -- frontcontroller에서 씀
    }

    public String getCreate(HttpServletRequest req, HttpServletResponse resp){

        System.out.println("Get /todo/create ");
        return "todo/create";


    }

    public String postCreate(HttpServletRequest req, HttpServletResponse resp) {//6-2

        return "redirect:/todo/list";//6-3
    }
}
