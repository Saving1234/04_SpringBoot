package section04;

import common.MemberDAO;
import common.MemberDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import section02.javaconfig.ContextConfigure;

public class Application {
    public static void main(String[] args) {

        //DTO 같은 경우엔 NEW를 써서 생성??

        MemberDTO javaMember1 = new MemberDTO(1,"user01", "pass01", "홍길동");
        MemberDTO javaMember2 = new MemberDTO(1,"user01", "pass01", "홍길동");

        System.out.println("(javaMember1==javaMember2) = " + (javaMember1==javaMember2)); //주소값이 다르니 false


        ApplicationContext context = new AnnotationConfigApplicationContext(ContextConfigure.class);
        // bean 관리하는 객체.

        // new로 객체 안만들었어도, ApplicationContext가 만들어줌.
        // 빈 찾아온 것.

        MemberDTO member1 =  context.getBean("member", MemberDTO.class);
        MemberDTO member2 =  context.getBean("member", MemberDTO.class);
        System.out.println("member1 = " + (member1==member2));

    }

}
