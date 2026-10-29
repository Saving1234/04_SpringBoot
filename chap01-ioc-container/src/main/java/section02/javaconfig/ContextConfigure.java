package section02.javaconfig;


import common.MemberDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration이 정의된 클래스가 설정파일이라는 것을 의미.
//bean으로 등록.
@Configuration("configurationSection02")
public class ContextConfigure {

    //bean으로 등록.
    @Bean(name = "member")
    public MemberDTO getMember(){

        return new MemberDTO(1, "user01", "pass01", "옹달샘");
    }

}
