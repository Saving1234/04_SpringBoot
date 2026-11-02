package org.example.filterandintercepter.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//interceptor는 저장.
//MVC에 저장해야하기에,.
//WebMvcConfigurer : springmvc 설정.(cors, 인터셉터, url 매핑 등) 개발자가 커스텀할 수 있게 해주는 인터페이스.
@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final StopWatchInterceptor stopWatchInterceptor;

    public WebConfiguration(StopWatchInterceptor stopWatchInterceptor) {
        this.stopWatchInterceptor = stopWatchInterceptor;
    }

    @Override  // 인터셉터 추가. 즉, 저장.
    public void addInterceptors(InterceptorRegistry registry) {
        //InterceptorRegistry : spring MVC에서 관리하는 등록 객체. 스프링이 등록해주는 것?
        registry.addInterceptor(stopWatchInterceptor)
                .addPathPatterns("/stopwatch", "/interceptor/**")
                .excludePathPatterns("/css/**", "/image/**", "/js/**", "/error/**");
    }
}
