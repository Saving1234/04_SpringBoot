package common;

import org.springframework.stereotype.Component;

// @Component가 붙어있으면 스캔할 때 자동으로 스프링 컨텍스트에 bean 등록됨.

@Component
public class TestDTO {


    private int no;
}
