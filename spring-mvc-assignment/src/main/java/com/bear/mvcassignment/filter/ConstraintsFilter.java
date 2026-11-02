package com.bear.mvcassignment.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ConstraintsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        if( servletRequest.getRequestURI().equals("/parameters/search") &&!"자바스크립트".equals(request.getParameter("keyword"))
        && !"파이썬".equals(request.getParameter("keyword")) && !"자바".equals(request.getParameter("keyword"))&& !"스프링".equals(request.getParameter("keyword"))){
            servletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "자바스크립트는 배우지 않았음");
            System.out.println("doFilter : 자바스크립트 배우지 않음.");
            return;
        }

        chain.doFilter(request,response);

    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }


}
