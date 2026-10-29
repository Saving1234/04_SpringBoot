package bear.section01.subsection01.field;

//서비스 클래스 하나 만들어서 DAO와 연결.
//스프링으로 주입.

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext("bear.section02");

        Bookservice bookservice = context.getBean("bookServiceFeild", Bookservice.class);

        bookservice.selectAllBooks().forEach(System.out::println);
        System.out.println(bookservice.searchBookBySequence(1));

    }
}
