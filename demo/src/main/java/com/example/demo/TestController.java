package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test(){

        return "테스트메소드입니다";
    }

    //여러개의 데이터를 한번에 가져올 수 있음.
    //유지보수성 좋아짐.
    //같은 도메인끼리 묶어놓을 수 있다. --> member는 membercontroller에 다 넣고, produectController에도 prodect 넣는 것처럼.
    // 묶어 놓아 놓은 것
    @GetMapping(value = "/index", produces = "text/html; charset=UTF-8")
    public String test2(){

        return """
                <h1>인덱스 화면입니다</h1>
                <h3>이건 예시입니다</h3>
                
                """;
    }

}
