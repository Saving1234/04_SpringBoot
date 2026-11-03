package com.bear.mvcassignment.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
 class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        if(session == null || session.getAttribute("nickname") == null){
            response.sendRedirect("/session/home");
            return false;
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        //로그인 공통작업.
        //000님 환영합니다. -> nickname +"님 환영합니다."
        HttpSession session = request.getSession();
        request.setAttribute("nickname", session.getId()+"님 환영합니다");


    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

        //컨트롤러에서 나온 예외 정리.
        //뷰랜더링 끝난 후에, 자원을 닫아줄 때 활용.
        //활용한 자원: request, response,
        //session과 request, response는 servlet이 관여하다보니, 닫아줄 필요는 없다.
        //servlet, 즉 톰캣이 닫아줌.

        System.out.println("afterCompletion 종료");



    }
}

