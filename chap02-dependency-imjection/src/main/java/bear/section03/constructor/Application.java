package bear.section03.constructor;

//서비스 클래스 하나 만들어서 DAO와 연결.
//스프링으로 주입.

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/*
        field : 값을 집어넣는다 ()
        setter : setter 호출해서 값  ()
        constructor : constructor 호출해서 값.  (객체 만들 때 값 할당.) -> 불변객체의 성질을 띄기에 생성자 주입.
        -- final을 설정하면 생성자 바꿀 수 없으니 ... 좋음..  (constructor 주입)
        -- 다른 건 객체를 만들고 주입을 하기 때문에 final 설정을 할 수가 없음.
 */

public class Application {

    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext("bear.section03"); //현재 이 클래스 경로.

        Bookservice bookservice = context.getBean("bookServiceConstructor", Bookservice.class); //북 서비스 빈의 타입을 뭘로 할건지.

        System.out.println(bookservice.searchBookBySequence(1));

    }
}
