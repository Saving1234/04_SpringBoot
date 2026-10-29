package section02.javaconfig;

import common.MemberDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

//javaconfigration ? 만듦?

public class Application {

    public static void main(String[] args) {
        // application context 만들 것.
        // xml 파일로 설정정보 담아서 만들어보기.

        ApplicationContext context = new AnnotationConfigApplicationContext(ContextConfigure.class);
        // bean 관리하는 객체.

        // new로 객체 안만들었어도, ApplicationContext가 만들어줌.
        // 빈 찾아온 것.
        MemberDTO member1 = (MemberDTO) context.getBean("member");
        System.out.println("member1 = " + member1);

        // 클래스의 메타정보를 이용해 가져오는 방법
        // 해당 클래스의 빈을 가지고 오는 것.
        MemberDTO member2 = (MemberDTO) context.getBean(MemberDTO.class);
        System.out.println("member2 = " + member2);


        //xml은 잘 안쓰지만, 이런 설정도 있기에.

    }
}