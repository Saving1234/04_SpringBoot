package org.example.filterandintercepter.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/*
    Interceptor
    - DispatcherServlet 내부에서 선택된 Handler를 기준으로 동작.
    - Controller에 가까움(스프링 컨텍스트 내부). Handler 정보와 ModelAndView 사용 가능.
 */

@Component
public class StopWatchInterceptor implements HandlerInterceptor {
    //Controller 호출 전에 실행. 반환값이 true이면 다음단계로 진행, false이면 요청 처리 중단. block이 가능.
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("[3. interceptoer] 컨트롤러 호출 전.");

        //block.
        if (request.getRequestURI().equals("/interceptor/block") && !"granted".equals(request.getParameter("access"))) {
            System.out.println("[Interceptor] access 값이 없어 요청 차단!");

            response.sendError(HttpServletResponse.SC_FORBIDDEN, "access=granted 파라미터가 필요합니다.");
            return false;
        }

        request.setAttribute("interceptorStartTime", System.currentTimeMillis()); //실행되는 시간 여기에 저장.
        return true;
    }


    //Controller 호출 후 실행. view가 랜더링 되기 전에 실행. controller 호출 후.
    //ModelAndView가 존재하면 전달할 데이터를 추가하거나 수정 가능.
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
        modelAndView.addObject("interceptorInterval", "값!");

        long startTime = (long) request.getAttribute("interceptorStartTime");
        long interval = System.currentTimeMillis() - startTime;

        modelAndView.addObject("interceptorStartTime", interval);
        System.out.println("[5.interceptoer] 컨트롤러 호출 후. 뷰 랜더링 전.");
    }

    /*
        view 랜더링 포함한 요청 처리가 모두 끝난 뒤 실행., view 랜더링 이후.
        예외에 대한 정보 받을 수 있음.
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        System.out.println("[6.interceptoer ]뷰 랜더링 후.!!");

    }
}
