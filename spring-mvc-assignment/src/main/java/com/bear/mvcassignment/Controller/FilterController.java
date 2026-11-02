package com.bear.mvcassignment.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/filter")
public class FilterController {

    @RequestMapping("/block")
    public String block(){


        return "home";
    }
}
