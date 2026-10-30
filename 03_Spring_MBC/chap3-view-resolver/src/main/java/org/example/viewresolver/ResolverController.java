package org.example.viewresolver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



    @Controller
    @RequestMapping("/*")
    public class ResolverController {


        @GetMapping("string")
        public String stringReturning(Model model) {


            model.addAttribute("forwardMessage", "문자열로 뷰 이름 반환함...");


            return "result";
        }

        //void는 어떻게 html 파일을 읽어서 실행시키는지.
        //경로를 논리적 뷰로 인식.
        //viewResolber가 실행 : 논리적 뷰와 같은 파일로 들어가 실행시킴.
        @GetMapping("string-redirect")
        public String stringRedirect()  {

            //redirect : 리다이렉트를 지시하는 특별한 접두사
            return "redirect:/";
        }


        @GetMapping("string-redirect-attr")
        public String stringRedirectFlashAttribute(RedirectAttributes rAttr) {

            rAttr.addFlashAttribute("flashMessage1", "RedirectAttribute를 사용해 redirect..");

            return "redirect:/";
        }

        @GetMapping("modelandview")
        public ModelAndView modelAndViewReturning(ModelAndView mv) {


            mv.addObject("forwardMessage", "ModelAndView를 이용한 모델과 뷰 반환");
            mv.setViewName("result");

            return mv;
        }

        @GetMapping("modelandview-redirect")
        public ModelAndView modelAndViewRedirect(ModelAndView mv)  {

            mv.setViewName("redirect:/");

            return mv;
        }

        //flash attribute : 리다이렉트 대상의 다음 요청에 자동으로 전달. -> 한번 사용한 뒤 제거되는 일회용 데이터.
        @GetMapping("modelandview-redirect-attr")
        public ModelAndView modelAndViewRedirect(ModelAndView mv, RedirectAttributes rttr) {

            rttr.addFlashAttribute("flashMessage2", "ModelAndview를 이용한 RedirectAttribute"); //데이터 전달
            mv.setViewName("redirect:/"); //PATH 지정

            return mv;
        }
    }


