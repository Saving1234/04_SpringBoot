package section01.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

// Aspect :  로깅, 성능측정, 예외 처리같은 공통기능을 모아둔 클래스.
// Advice : 실제 공통기능이 작성된 메서드.

//ApplicationContext == SpringContainer == iocContainer ...

@Component
@Aspect

public class MemberAspect {

    //excution() 기본형태 해보자.
    // excution(반환형 패키지.클래스 메서드명(매개변수))
    // * :  무엇이든 가능
    // (..) : 매개변수 갯수와 타입은 무엇이든 가능.
    @Pointcut("execution(* section01.aop.MemberServiceImpl.processMember(..))") //memberImpl.
    public void advicePointCut(){} //memberImpl의 포인트 컷을 가진 메서드.

    @Before("advicePointCut()")
    public void before (){
        System.out.println("아아아");
    }

    // before  후, 예외발생 안했으니까 AfterReturning으로.
    // returning: 메서드가 실행끝난 후의 결과값.
    @AfterReturning(value = "advicePointCut()", returning = "resulu")
    public void afterReturingAdvice (Object resulu){
        System.out.println("반환값 :" + resulu );
    }

    @After("advicePointCut()")
    public void AfterAdvice (){
        System.out.println("after 메서드 실행 후..");
    }

    @AfterThrowing(pointcut = "advicePointCut()", throwing =  "exception")
    public void afterThrowingAdvice(Exception exception){
        System.out.println("예외"+exception.getMessage());
    }

    @Around("advicePointCut()")  //타겟 위치 포인트를 jointPoint?? //어드바이스 동작 위치 지정.
    private Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable{

        long startTime = System.nanoTime();
        System.out.println(" arround실행 전.");

          try {
              //proceed()가 Target 메서드 실행.
              return joinPoint.proceed();}
            finally {
              long excutionTime = System.nanoTime() - startTime;
              System.out.println("around 실행시간 :" + excutionTime + "ns");
          }
    }

    //여기 안에서만 찾는다.
    // whthin : 클래스 내부 선택.
    @Before("within(section01.aop.MemberServiceImpl) && execution(* selectMembers(..))")
    public void withinPointCut(){
        System.out.println("[within] MemberService 적용");
    }


    // 멤버서비스 타입을 가진 모든 걸.
    @Before("this(section01.aop.MemberServiceImpl) && execution(* checkMember(..))")
    public void thisPointCut(){
        System.out.println("[within] MemberService 적용");
    }

    //args : 해당 매개변수를 가진 메소드에 적용
    // 멤버서비스 타입을 가진 모든 걸.
    //args 는 메개변수 명 넣음.
    @Before(value = "args(name, age) && execution(* registerMember(..))", argNames = "name,age")
    public void argsPointCut(String name, int age){ //여기서 매개변수 받기.
        System.out.println("[args] MemberService 적용");
    }

    @Before("@annotation(section01.aop.AopLog)") //어떤 어노테이션을 넣을건지. 어노테이션의 경로 넣기.
    public void annotationPointCut() { //여기서 매개변수 받기.
        System.out.println("[annotation] MemberService 적용");

    }
    // excution(반환형 패키지.클래스.메서드명(매개변수))

    @Before("execution(void section01.aop.MemberServiceImpl.selectMember(..))")
    public void execution(){
        System.out.println("[exection] selectMember 적용");
    }


}
