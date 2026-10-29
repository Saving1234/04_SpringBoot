package section03.Annotationconfig;

import common.MemberDTO;
import common.TestDTO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//javaconfigration ? 만듦?

public class Application {

    public static void main(String[] args) {
        // application context 만들 것.
        // xml 파일로 설정정보 담아서 만들어보기.

        ApplicationContext context = new AnnotationConfigApplicationContext(ContextConfigure.class);
        // bean 관리하는 객체.

        String[] beanNames = context.getBeanDefinitionNames();

        for(String beanName : beanNames){
            System.out.println("beanName = " + beanName);
        }


        //xml은 잘 안쓰지만, 이런 설정도 있기에.

    }
}