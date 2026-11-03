package org.example.restapi.section01.response;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
    ResponseBody
    반환 값이 응답 본문(body)에 직접 쓰여짐. view 거치지 않음.
    주로, JSON, XML 같은 형식의 데이터를 HTTP로 반환하고자 할 때 사용.

 */

//@Controller
//@ResponseBody  //이 두개가 묶인 것이 RESTCONTROLLER
@RestController
@RequestMapping("/response")

//경로 확인
//어노테이션 확인
//오타 확인.

public class ResponseRestController {

    //JSON.
    //자바 스크립트 상에서 인지할 수 있는 객체형태의 문자열.
    //배열 : [] : 리스트
    //객체 : {} : 클래스 타입, 맵.

    @GetMapping("/hello")
    public String hello() {
        return "Hello World!";
    }

    @GetMapping("/random")
    public int random() {
        return (int) (Math.random() * 10) + 1;
    }

    @GetMapping("/message")
    public Message message() {
        return new Message(200, "만화");
    }

    @GetMapping("/list")
    public List<String> getList() {
        return List.of(new String[]{"사과", "바나나", "복숭아"});
    }

    @GetMapping("/map")
    public Map<Integer, String> getMap() {
        List<Message> messageList = new ArrayList<>();
        messageList.add(new Message(200, "정상응답"));
        messageList.add(new Message(404, "페이지를 찾을 수 없습니다"));
        messageList.add(new Message(500, "개발자 잘못"));

        return messageList.stream().collect(Collectors.toMap(Message::getHttpStatusCode, Message::getMessage));
        //collect: 스트림 연산을 통해 가공된 데이터들을 자료구조(List, Map, Set, Get 등)나 형태로 모아주는 것.
    }

    //produces 설정을 하지 않으면 text/html로 응답을 하기때문에 이미지가 전송되지 않음
    @GetMapping(value = "/image",produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getImage() throws IOException {
        return getClass().getResourceAsStream("/static/고양이1.png").readAllBytes();
        //어떤 파일 확장자인지 설정해줘야함.
    }

    /*
        http 요청 대한 응답을 구성항 떄 사용하는 클래스
        응답본문, 상태코드, 응답헤더 등을 포함한 http 응답 제어할 수 있다.
     */
    @GetMapping("/entity")
    public ResponseEntity<Message> getEnity(){  //상태코드 제어할 수 있음.
//        return ResponseEntity.ok(new Message(200,"응답성공"));
        return ResponseEntity.status(500).body(new Message(200,"응답성공"));
    }


}




