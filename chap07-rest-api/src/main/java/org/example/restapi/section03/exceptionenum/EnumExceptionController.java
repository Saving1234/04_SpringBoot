package org.example.restapi.section03.exceptionenum;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/*
    비즈니스로직의 예외들을 처리해주는 예외처리 클래스 만들고자 함.

    예: 회원가입 규칙. 중복 저장X,

    예외파일을 너무 많이 만들면 관리하기 힘드니,
 */

@RestController
@RequestMapping("/enum-errors")

public class EnumExceptionController {

    private final Map<Integer,String>users = Map.of(1, "홍길동", 2, "유관순");

    @GetMapping("/users/{userNo}")
    public ResponseEntity<Map<String, Object>> findUser(@PathVariable int userNo){

        if(userNo <1){
            throw new BusinesException(ErrorCode.INVALID_USER_NUMBER );
        }
        String userName = users.get(userNo);

        if (userName== null){
            throw new BusinesException( "회웝번호" + userNo + "에 해당하는 회원이 없습니다", ErrorCode.USER_NOT_FOUND);
        }


        return ResponseEntity.ok().build();
    }


}
