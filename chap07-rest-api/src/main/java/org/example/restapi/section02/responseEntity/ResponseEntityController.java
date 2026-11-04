package org.example.restapi.section02.responseEntity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/entity")
//데이터 대행.
//DTO에 값 넣기.
public class ResponseEntityController {

    private List<UserDTO> users;

    public ResponseEntityController() {
        this.users = new ArrayList<>();

        users.add(new UserDTO(1,"user01", "passo1", "홍길동", new Date()));
        users.add(new UserDTO(2,"user02", "passo2", "장춘길", new Date()));
        users.add(new UserDTO(3,"user03", "passo3", "공동동", new Date()));
    }

    //USER 목록 전체 조회.
    //url만으로 무슨 동작을 하려고 하는지 유추가 가능.
    // 유저 목록 전체 조회
    //자원 주소 명확하게 url에.
    @GetMapping("/users")
    public ResponseEntity<ResponseMesssage> findAllUsers() {

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", users);

       ResponseMesssage  responseMessage = new ResponseMesssage(200, "조회 성공", responseMap);

        /*
         * applicadtion/json : HTTP의 응답이 JSON 형식의 데이터임을 의미
         * */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                new MediaType("application", "json", StandardCharsets.UTF_8)
        );

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    //특정 id의 유저만 조회.
    //특정 user 상세 조회.
    //user 한 ㅕㅁㅇ.

    // 특정 유저 상세 조회
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMesssage> findUserByNo(@PathVariable int userNo) {
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

    //신규유저 등록. -> 유저 정보 -> 조회를 할 수 있는 api와 연결.
    //heteoas. 적용.
    //post 요청 회원가입 -> 가입된 회원으로 할 수 있는 요청.
    //-> 가입된 회원을 조회하는 url.
    //@RequestBody : Http 요청의 본문에 담긴 데이터를 java 객체로 변환하여 매개변수에 전달하는 역할.
    @PostMapping("/users")
    public ResponseEntity<?>registUser(@RequestBody UserDTO newUser){

        System.out.println("newUser = " + newUser);

        int lastUserNo = users.get(users.size()-1).getNo();
        int newUserNo = lastUserNo+1;

        newUser.setNo(newUserNo);
        newUser.setEnrollAT(new Date());

        users.add(newUser); //저장.

        //일반적으로 ok, created, 상태코드 200 많이 씀??
        return ResponseEntity
                .created(URI.create("/entity/users" + newUserNo))
                .build();
    }

    //기본 유저 정보





    //기본 유저 정보 삭제

    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?>removeUser(@PathVariable int userNo){

        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo)
                .toList().get(0);

        users.remove(foundUser);

        return ResponseEntity  //ResponseEntity로 감쌈.
                .noContent()
                .build();

        //공통응답코드 만드는 이유?


    }

    //특정 회원의 정보를 전체 수정.
    //put?

    @PutMapping("/users/{userNo}")
    public ResponseEntity<?>updateUser(@PathVariable int userNo, @RequestBody UserDTO user){
        System.out.println(users);
        System.out.println("userNo = " + userNo);

        UserDTO foundUser = users.stream()
                .filter(param -> param.getNo() == userNo)
                .toList().getFirst();  //userNo와 일치한 리스트를 가져옴.

        //받아온 값 user number와 일치한 id, pwd, name
        //id, pwd, name을 dto에 값 넣기.
        //dto에 값 넣으려면 어떻게 해야할까? -- user
        //user number를 저장. -> 어디에? userDTO 리스트에 넣어서 저장.
        //while에 next 넣어서 다음이 없을 때까지 반복.
        //list.set에 넣기.
        //user




       foundUser.setId(user.getId());
        foundUser.setPw(user.getPw());
       foundUser.setName(user.getName());



        return ResponseEntity.ok(users);

    }



}
