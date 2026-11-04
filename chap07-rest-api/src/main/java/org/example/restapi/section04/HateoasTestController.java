package org.example.restapi.section04;


import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.*;

//3단계.
//응답에 다음동작을 링크에 포함.
//데이터응답. 이걸 할 수있는 요청을 연결.
//직접 링크를 넣어서 할 수도 있지만 라이브러리로 번거롭지 않게 하겠다.

//Hateoas (Hyper ... 철자 검색해서 확인.)
//aplication의 상태처럼 쓰자.
//RestAPI 응답에 현재 데이터 뿐만 아니라, 해당 데이터와 관련된 다음 요청의 링크도 함께 제공하자!!
//링크 생성 라이브러리.
//어셈블러

@RestController
@RequestMapping("/hateoas")
public class HateoasTestController {

    private List<UserDTO> users;
    private final DtoModelAssembler assembler;


    //스프링이 여기에 주입.
    // userDTO를 경로랑 같이 쓰면 안됨. 이 클래스의 새 객체 USERDTO를 만들어야, 스프링이 주입해줌.
    public HateoasTestController(DtoModelAssembler assembler) {
        this.assembler = assembler;
        this.users = new ArrayList<>();

        users.add(new UserDTO(1, "user01", "pass01", "홍길동", new Date()));
        users.add(new UserDTO(2, "user02", "pass02", "유관순", new Date()));
        users.add(new UserDTO(3, "user03", "pass03", "이순신", new Date()));
    }


    @GetMapping("/user/{userNo}")
    public ResponseEntity<ResponseMesssage> findUserByUseNo(@PathVariable int userNo) {
        /*
         * applicadtion/json : HTTP의 응답이 JSON 형식의 데이터임을 의미
         * */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                new MediaType("application", "json", StandardCharsets.UTF_8)
        );

        UserDTO foundUser = new UserDTO();

        // 일치하는 회원 찾기
        for (UserDTO user : users) {
            if (user.getNo() == userNo) {
                foundUser = user;
                break;
            }
        }

        // 응답 데이터
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", foundUser);

        // 메서드 체이닝 + 빌더 패턴
        return ResponseEntity
                .ok() // 200 상태코드
                .headers(headers)
                .body(new ResponseMesssage(200, "조회성공", responseMap));
    }

    //전체조회하게되면 -> 각각의 유저정보가 전달 -> 해당정보를 단건조회할 수 있게 링크를 제공.???
//    "rel": "self",  데이터 자신.
//            "href": "http://localhost:8080/hateoas/user/3  자기 자신의 조회 url을 보여줌.
    //sel
    @GetMapping("/users")
    public ResponseEntity<ResponseMesssage> findAllUsers() {

        //사용자 목록을 EntityModel로 변환하기 위해 Assembler의 toModel의 메소드 사용.
        List<EntityModel<UserDTO>> userWithRel = users.stream()
                .map(assembler::toModel)
                .toList();

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", userWithRel);

        ResponseMesssage responseMessage = new ResponseMesssage(200, "조회 성공", responseMap);


        /*
         * applicadtion/json : HTTP의 응답이 JSON 형식의 데이터임을 의미
         * */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                new MediaType("application", "json", StandardCharsets.UTF_8)
        );

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }


}
