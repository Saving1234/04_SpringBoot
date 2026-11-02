package org.example.filterandintercepter.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {

    //filter는 servlet 기술이므로 servlet container에 등록해야함.
    //stopWatchFilterFilterRegistration : Spring Context에 Bean으로 Filter를 등록할 수 있게 해주는 클래스.
    // 어떤 Filter 객체를 등록할지
    // 어떤 url에 적용할지
    // 여러 filter 중 몇 번째로 실행할지. 설정해줘야한다.
    @Bean
    public FilterRegistrationBean<StopWatchFilter> stopWatchFilterFilterRegistration(){

        FilterRegistrationBean<StopWatchFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new StopWatchFilter()); //FILTER객체를 Servlet Container에 등록.!! 1.
        registration.addUrlPatterns("/stopwatch", "/filter/*"); //이 url로 요청이 왔을 때, 이 필터 동작하겠다. 2.
        registration.setOrder(1); // 필터 적용 순서 명시(숫자가 작을 수록 먼저 실행됨.)  3.
        registration.setName("stopWatchFilter");

        return registration;
    }

}
