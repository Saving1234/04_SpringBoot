package com.bear.mvcassignment.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final logInterceptor logInterceptor;
    private final SessionInterceptor sessionInterceptor;


    public WebConfiguration(logInterceptor logInterceptor, SessionInterceptor sessionInterceptor) {
        this.logInterceptor = logInterceptor;
        this.sessionInterceptor = sessionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {



        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/*");

        registry.addInterceptor(sessionInterceptor)
                .

    }
}
