package org.example.handletmethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/first/*")
public class FirstController {

    //void일 경우 하게되면 요청경로(path)를 논리적인 view 이름으로 사용. => view가 바로 적용되는 것인지?

    @GetMapping("regist")
    public String registMethod(){

        return "first/regist";  //first 패키지/regist 파일.
    }

    //httpservletRequest나 WebRequets나 가능.
    @PostMapping("regist")
    public String registMenu(Model model, HttpServletRequest request){
        System.out.println(request.getContextPath());  //request의 내용들을 꺼내서 볼 수 있다.
        System.out.println();

        String name = request.getParameter("name");
        System.out.println("name = " + name);
        int price = Integer.parseInt(request.getParameter("price")); //타입을 지정해줘야함. 문자열 -> 정수열로 변환시켜야하니.
        System.out.println("price = " + price);
        int categoryCode = Integer.parseInt(request.getParameter("categoryCode"));
        System.out.println("categoryCode = " + categoryCode);

        model.addAttribute("message", name + "을 신규메뉴 목록의" + categoryCode + "번, 가격은" + price+ "원으로 추가했습니다");

        return "first/messageFrinter";
    }

    @GetMapping("modify")
    public void modify(){


    }

    //요청할 때 파라미터를 핸들러 메소드의 매개변수에 바인딩.
    // RequestParm 생략 가능 : 매개변수 둘 다 타입이 같을 때.
    //required : 기본값은 true. 필수파라미터가 없으면 400 예외 발생.
    //defaultValue : 값이 없거나 비어있을 때 사용할 기본값 지정.
    @PostMapping("modify")
    public String modifyMenu(Model model, @RequestParam("modifyName") String modifyName, @RequestParam(defaultValue = "0") String modifyPrice){

        String message = modifyName + "을" + modifyPrice + "원에 책정";
        model.addAttribute("message", message);

        return "first/messageFrinter";
    }
    //s
    @GetMapping("search")
    public void search(){}  //void는 return 값 안넣어도 view를 통해 search 파일로 이동.

    //
    // @ModelAttribute : 여러 요청 파라미터를 DTO 같은 객체에 바인딩.
    // 핸들러 메서드의 매개변수로 전달되는 동시에 Model에도 자동으로 추가된다.
    // Model에 담길 변수는 이름을 작성하지 않으면, 클래스명의 소문자 카멜케이스로 저장된다.
    @PostMapping("search")
    public String searchMenu(Model model, @ModelAttribute("menu") MenuDTO menuDTO){  //"menu" Model 담을 때 이름.

        return "first/seachResult";

    }

    @GetMapping("login")
    public void login(){}

    //session : 사용자 별 정보를 일정기간동안 보관하는 공간.
    // 로그인 상태, 장바구니 같은 임시정보 유지할 때 사용.
    // -key-value 쌍으로 보관
    // 동작과정
    /*
         1. 사용자가 처음, 서버에 요청
         2. 서버가 사용자 전용 세션을 만듦(JSESSIONID : 식별키)
         3. 서버가 사용자에게 응답할 때 JSSEIONID 쿠키 전달
         4. 브라우저가 이후 요청마다 JSESSIONID 쿠키 전송.
         5. 서버가 해당 JSESSIONID로 사용자의 세션 조회
     */
    @PostMapping("login")  //@RequestParam 생략.
    public String loginSession(HttpSession session,String id, String pwd){

        session.setAttribute("id", id);

        return "first/loginResult";
    }

    @GetMapping("session-check")
    @ResponseBody
    // 파일 안만들어도 상관이 없는건지??
    //브라우저에서 세션에 로그인하면, JSESSIONID를 발급. -> 이 사용자 식별, 사용했던 섹션 정보 알려줌. / 사용자마다 다른 화면이 나오게 됨.
    //기본 크롬. secret 크롬. 크롬에서 다른 아이디로 로그인 할 때마다 jsessionid가 달라지는지?

    public String sessionCheck(HttpSession session){

        return session.getId() + session.getAttribute(session.getId());
    }

    @GetMapping("logout")
    public String logoutTest(HttpSession session){

        session.invalidate(); //삭제.
        return "first/loginResult";
    }

    //json 전달할 때 사용.
    @GetMapping("body")
    public void body(){

    }

    //쿼리형식으로 들어오기때문에 인코딩해주면 된다.
    /*
        RequestBody : HTTP 요청 본문을 읽어서 핸들러 메소드의 매개변수로 전달.
        본문에 있는 것을 가져온다.
        일반form 요청은 쿼리문자열의 형태. json은 jackson 메시지지로 전달.
     */

    //contentType을 보여주기 위한 것. F12 개발자모드에서 볼 수 있다.
    // 보고 싶은 것 어노테이션 써서 확인.
    @PostMapping("body")
    public void bodyTest(@RequestBody String body, @RequestHeader("content-type")String contentType, @CookieValue(value = "JSESSIONID", required = false) String cookieType)throws UnsupportedEncodingException {
        System.out.println("body : " + URLDecoder.decode(body, StandardCharsets.UTF_8));
    }

    

    }


