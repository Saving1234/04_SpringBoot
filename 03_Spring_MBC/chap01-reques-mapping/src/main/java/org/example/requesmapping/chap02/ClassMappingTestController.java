package org.example.requesmapping.chap02;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//끌 때는 terminate.
//dis... 누르면 port 직접 찾아서 꺼야함. 그 방법은??
// 컨틀롤러 - 리퀘스트 매핑 안에 있는 메소드 다 핸드링메소드.
@Controller
@RequestMapping("/order/*") // *: /order 이후 뭐가 오든지 여기에서 처리하겠다. 이 클래스에선 모든 경로에 /order가 붙음.(클래스에 붙였으니까.)
public class ClassMappingTestController {

    @GetMapping("/regist")   //이 클래스의 모든 경로가 오더로 .
    public String registOrder(Model model) {

        model.addAttribute("message","registOrder 메서드");
        return "mappingResult";
    }

    // /order/modify
    // /order/delete

     //이 클래스의 모든 경로가 오더로 .

// 여러개의 요청 매핑 : @RequestMapping의 value 속성에 중괄호를 이용해 매핑할 url 나열.
@RequestMapping(value = {"modify", "delete"}, method =RequestMethod.POST) //요청 들어올 때, 어떤식으로 처리할건지.
    public String modifyAndDelete(Model model) {

        model.addAttribute("message","modifyAndDelet 메서드");
        return "mappingResult";
    }

    //PathVariable : URL에서 변수를 받아올 수 있다..
    //PathVariable(Path에 적힌 변수명) : URL에서 변수를 받아올 수 있다..
    //PathVariable 타임 path에 적힌 변수명
    @GetMapping("/detail/{orderNo}")  // /detail/5 라고 하면 이것을 핸들러메소드로 가지고 올 수 있다. 이 변수를 가지고 와 사용.
   // public String selectOrderDetail(Model model, @PathVariable("orderNo") int a){  //orderNo를 int로 받겠다는 것인지???
        public String selectOrderDetail(Model model, @PathVariable int orderNo){

            model.addAttribute("message", "selectOrderDetail 메소드");
            return "mappingResult";
    }

    @RequestMapping
    // 뭘 넣어도 여기 실행.
    // 이건 암호화 x. url 공개가 됨. 아직 암호화하는 url 안배움.
    public String anyRequest(Model model){  //

        model.addAttribute("message", "여기에서 요청 처리.");
        return "mappingResult";
    }

}
