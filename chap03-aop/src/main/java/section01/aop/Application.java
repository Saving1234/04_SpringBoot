package section01.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;


public class Application {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(ContextConfigureration.class);

        MemberService memberService = context.getBean(MemberService.class);

        System.out.println("====TAGET 실행 ===");
        //System.out.println(memberService.processMember(false));

        //whthin 예제
       memberService.selectMembers();

       memberService.checkMember();

       memberService.registerMember("오", 1);

       memberService.updateMember();

       memberService.selectMember();

    }

}
