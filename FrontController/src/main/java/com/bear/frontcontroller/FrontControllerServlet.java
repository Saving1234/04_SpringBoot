package com.bear.frontcontroller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet(name = "frontControllerServlet", value = "/")
public class FrontControllerServlet extends HttpServlet {

    //요청을 처리하는 controller 객체.
    private final HomeController homeController = new HomeController();

    //"/WEB-INF/views/"
    private static final String VIEW_PREFIX = "/WEB-INF/views/"; //11
    private static final String VIEW_SUFFIX = ".jsp"; //13

    TodoController todoController = new TodoController();

    //전체 uri에서 어플리케이션의 Contex 길이만큼 뺀다.
    private String getRequestPath(HttpServletRequest req) {

        String requestURI = req.getRequestURI(); // 서버에 들어온 요청 주소의 식별자 경로(contextPath + servlet)
        System.out.println("requestURI = " + requestURI);
        String contextPath = req.getContextPath(); //식별자 역할. 서블릿 주소 앞에 오는 것.(서블릿이 상세주소라면, 컨텍스트는 기본주소.)
        System.out.println("contextPath = " + contextPath);

        // substring : 뺀다. contextpath길이 만큼 뺀다.
        // 빼는 이유 : 프로젝트 이름 바뀌게 되면 servlet만 바꾸는 것이 아니라, 컨텍스트도 다 바꿔야함.
        // 그래서 유지보수 겁나 빡쎄짐.
        //그래도 굳이 만드는 이유는 컨텍스트패스가 있어야 경로로 뭘 보내니까.
        return requestURI.substring(contextPath.length());

    }

    //get 요청 경로에 맞는 Controller 메소드를 직접 호출.
    private String dispatchGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // "/"
        String requestPath = getRequestPath(req);

        switch (requestPath) {
            case "/":
                return homeController.getIndex(req, resp);
            case "/todo/list":
                return todoController.getList(req,resp); // "todo/list"
            case "/todo/create":
                return todoController.getCreate(req,resp);
            default:
                return null;
        }
    }

    private void render(String viewName, HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException { //8

        if (viewName == null) { //9 null일 경우에.
            resp.sendError(HttpServletResponse.SC_NOT_FOUND); //9-1
            return; //9-2
        }

        // "/todo/list";
        if (viewName.startsWith("redirect:")) { // 어떤 문자열로 시작하는지.
            //10
            String redirectPath = viewName.substring("redirect:".length()); // redirect 하라는 표시를 지우라. 여기에서 쓸모를 다했으니, 지움.
            //11
            resp.sendRedirect(req.getContextPath() + redirectPath); //response : 사용자에게 출력. => path + /todo/create
            //12
            return; //13 여기에서 모든 값은 끝냄. 아래부턴 새로운 값?
        }//14

        String viewPath = VIEW_PREFIX + viewName + VIEW_SUFFIX; //"/WEB-INF/views/redirect:.jsp" 인가??
        //15

        //jsp 경로를 추가한 RequestDispatcher 객체 생성
        RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath); //요청을 다른 자원으로 보냄. viewpath를 보냄.
        //16

        //  해당경로로 forwarding
        dispatcher.forward(req, resp); //포워드로, request/response에 포워드로 viewpath 보냄. //17
    }//18

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        String viewName = dispatchGet(req, resp);
        render(viewName, req, resp); // render

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {//1

        //

        // "redirect:/todo/list";
        String viewName = dispatchPost(req,resp); //2
        render(viewName, req, resp);//7


    }

    // Post 요청에 대한 처리
    private String dispatchPost(HttpServletRequest req, HttpServletResponse resp) {//3

        // "/todo/create"
        String requestPath = getRequestPath(req);//4

        switch (requestPath) {//5
            case "/todo/create"://6
                return todoController.postCreate(req, resp); //6-1
            default:
                return "redirect:/todo/list";
        }

    }
    // todo생성이 반복되지 않게 redirect 시키기 (목록 주소로)
    // redirect를 하는 이유는 중복 생성을 막기 위해서
    public String postCreate(HttpServletRequest req, HttpServletResponse resp) {

        System.out.println("Post /todo/create ");

        return "redirect:/todo/list";
    }
    }

