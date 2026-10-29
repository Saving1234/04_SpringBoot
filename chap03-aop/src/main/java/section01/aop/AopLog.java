package section01.aop;


// 사용자 정의 어노테이션
// 필요한 기능을 구분하기 위해 직접 만드는 어노테이션.

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//여기서 타겟은, 어노테이션 붙이는 위치를 넣는 것.,
@Target(ElementType.METHOD)

//어노테이션 정보를 언제까지 유지할지.
@Retention(RetentionPolicy.RUNTIME)
public @interface AopLog {  // 이렇게 하면 어노테이션 만들어짐.



}
