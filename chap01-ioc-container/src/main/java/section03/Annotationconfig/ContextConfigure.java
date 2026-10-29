package section03.Annotationconfig;


import common.MemberDAO;
import common.MemberDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

//@Configuration이 정의된 클래스가 설정파일이라는 것을 의미.
//bean으로 등록.
//하위에 있는 것들을 다 스캔해서 등록. 다만, 컴포넌트가 붙어있어야함. 컴포넌트들 스캔해서 등록.
//Component 계열 : @Controller , @Service, @Repository, @Entity..
//includeFilters : 그 범위 안에서 어떤 것을 포함할지.
//excludeFilters : 그 범위 안에서 어떤 것을 제외할지.


//component scan이 안될 때도 있음 : 외부 라이브러리 사용할 때.
// 외부라이브러리에 어노테이션을 붙일 수가 없어서.
// 외부 라이브러리에서는 직접 Bean을 등록함. JAVA CONFIG 방식.
// 객체 꼭 초기화해야 할 때 : 설정값을 넣어서 Bean으로 등록함.

@ComponentScan(basePackages = "common",useDefaultFilters = false,
includeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = (MemberDAO.class))})  //기본필터끄기 // common에 있는 컴포넌트 계열의 빈 찾아옴.
@Configuration("configurationSection03")
public class ContextConfigure {


}
