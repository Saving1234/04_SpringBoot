package org.example.paintro.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


////swagger : 어노테이션 방식.
/// //프론트엔드 개발자에게 보낼 때.
/// 첫번 째 방식.
//@OpenAPIDefinition(
//        info = @Info(title = "Rest API 사용설명서", description = "Rest Api 수업 자료입니다", version = "v1", contact = @Contact(name = "json")))
//@Configuration
//public class SwaggerConfig {
//
//
//}

    /*
        자바코드로 OPENAPI 설정방법.
        bean으로 객체 만들어서 한다. openAPI는 SWAGER니까 붙이는 것.
     */

@Configuration
//@EnableWebMvc // 설정방식 두번째.
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());  //apiInfo 메서드니까 () 넣어야함. //Info는 클래스로 넣어야함.
    }

    //이 방법으로도 swager가 나오는지 보는 것.
    private Info apiInfo(){
        return new Info()
                .title("사용설명서")
                .description("Rest ")
                .version("l.0.0");
    }

}


