package com.bear.mvcassignment.Controller;

import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/filter")
public class StopwatchController {

@GetMapping("/stopwatch")
    public String stopwatch() throws InterruptedException {
    Thread.sleep(1000);
    System.out.println("1초 경과");
    Thread.sleep(2000);
    System.out.println("2초경과");
    return "home";
}

}
