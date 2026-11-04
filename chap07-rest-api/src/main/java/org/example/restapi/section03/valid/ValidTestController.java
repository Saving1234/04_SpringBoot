package org.example.restapi.section03.valid;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/*/
VALIDATION 검증.

 Client(검증) -> server(검증.) 검증을 꼭 해야 막을 수있다. 특히 서버.

 클라이언트 요청은 조작이 쉽고, 데이터가 비정상적으로 들어올 가능성이 있기에 데이터 검증을 할 필요가 있다.
 validation이라는 라이브러리를 dependensi에추가.

 Dispatcher -> Controller -> 이 사이에서 validation 발생. ->HandlerMethod


 -- 재실행 --
 좌우명 : 정신차리자.

 */

@RestController
@RequestMapping("/valid")
public class ValidTestController {


//    //여기로 들어오는 값을 검증하겠다.
//    @PostMapping("/users")
//    public ResponseEntity<String> saveUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
//
//        // 1. 유효성 검증 실패 시 에러 메시지 반환
//        if (bindingResult.hasErrors()) {
//            // DTO에 설정해둔 message("아이디는 반드시...")를 가져옴
//            String errorMessage = bindingResult.getFieldError().getDefaultMessage();
//
//            // 400 Bad Request 상태코드와 함께 에러 메시지 전송
//            return ResponseEntity.badRequest().body(errorMessage);
//        }
//
//        // 2. 검증 통과 시 로직 수행
//        System.out.println(userDTO);
//
//        // 3. 성공 메시지 반환 (200 OK)
//        return ResponseEntity.ok("회원가입이 성공적으로 완료되었습니다!");


//    }


//여기로 들어오는 값을 검증하겠다.

    @PostMapping("/users")

    public ResponseEntity<Void> saveUser(@Valid @RequestBody UserDTO userDTO) {

        System.out.println(userDTO);


        return ResponseEntity.ok().build();

    }

}



