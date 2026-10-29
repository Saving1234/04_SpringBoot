package section04;


import common.MemberDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*\
     @Bean의 기본 Scope.
     Singleton :
     정해진 범위 안에서 객체의 인스턴스를 하나만 생성.
     해당 객체를 필요로 하는 곳들에서는 생성된 객체를 재사용하게끔 하는 개념.
 */

@Configuration("configurationSection04")
public class ContextConfigure {

    //bean으로 등록.
    @Bean(name = "member")
    public MemberDTO getMember(){

        return new MemberDTO(1, "user01", "pass01", "옹달샘");
    }

}
