package com.bear.mvcassignment.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfiguration {
    // 어떤 Filter 객체를 등록할지
    // 어떤 url에 적용할지
    // 여러 filter 중 몇 번째로 실행할지. 설정해줘야한다.


    @Bean
    public FilterRegistrationBean<LogFilter>registrationBean(){

        FilterRegistrationBean<LogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LogFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        registrationBean.setName("logFilter");

        return registrationBean;

    }

    @Bean
    public FilterRegistrationBean<ConstraintsFilter> constraintsFilter(){

        FilterRegistrationBean<ConstraintsFilter> constraintsFilter = new FilterRegistrationBean<>();
        constraintsFilter.setFilter(new ConstraintsFilter());
        constraintsFilter.addUrlPatterns("/parameters/search");
        System.out.println("contrainFilter");
        constraintsFilter.setOrder(2);

        return constraintsFilter;
    }



}
