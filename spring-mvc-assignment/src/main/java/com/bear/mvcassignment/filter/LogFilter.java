package com.bear.mvcassignment.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //일단 http 객체 만들어서 값넣기.
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        //값을 넣었으니, 그 값이 어떤 경로에 들어가면 실행을 시킬지 정의.
        httpRequest.getRequestURI();
        System.out.println("[logfilter] 요청 들어온 주소" + httpRequest.getRequestURI());

        chain.doFilter(request, response);



    }

    @Override
    public void destroy() {
        System.out.println("종료");
    }
}
