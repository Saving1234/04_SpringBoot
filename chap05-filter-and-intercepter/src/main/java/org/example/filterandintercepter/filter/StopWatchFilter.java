package org.example.filterandintercepter.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class StopWatchFilter implements Filter { //jakarta survlet filter 가져와야함.

//filter 객체가 생성되고 servlet contanier에 등록될 때 사용됨??
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[1 filter] init  어플리케이션 시작 시 한 번 호출");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    //다음 작업으로 넘겨주는 행위가 있어야 함.
        /*
            ServletRequest와 ServletResponse : HTTP 전용 타입이 아님.
            -> URL, HEADER, 상태코드 사용하기 위해 변환.

         */

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        System.out.println("httpServletRequest.getRequestURI()" + httpServletRequest.getRequestURI());
        if (httpServletRequest.getRequestURI().equals("/filter/block") && httpServletRequest.getHeader("X-Test")==null){
            System.out.println("/filter/block 요청이 들어옴...");
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "X-TEST 헤더가 필요합니다.");
            return; // chain.dofilter()를 호출하지 안으므로 dispatcherServlet, Controller 동작 x.  //넘겨줄 필터가 없으므로, disfatchservlet으로 넘김.
        }

        System.out.println("[2. filter- 전처리 시간 측정 시작");
        long startTime = System.currentTimeMillis();  //시작할 때 초 재기. 호출시간 시간 기록.
        System.out.println(startTime);


        chain.doFilter(request,response);  // -> 다음 필터 혹은 서블릿으로 이동.


        System.out.println("[7.filter - 후처리"); //마지막에 여기로.
        long endTime = System.currentTimeMillis(); // 로직 종료 후 시간 기록.

        System.out.println("[7.filter] - 전체 체인 수행시간 :" + (endTime-startTime)); //끝나는 시간과 시작 시간이 없으면 수행시간이 나올테니.
    }

    @Override
    public void destroy() {
        System.out.println("[99.filter] destroy - 어플리케이션 종료 시 한번 호출");
    }


}
