package org.example.restapi.section02.commonResponse;


/*
    모든 api에서 공통으로 사용할 응답 형식.
    제네릭 활용.

    @param <T> 무슨 타입이 오든 담겠다.
 */

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ApiResponse<T> {

    private int status;
    private String message;
    private T data;

    /*
        정적 팩토리 메서드
        의미를 부여. 생성자와 또같이.
        --> 객체 생성과정을 캡슐화하여 객체를 생성, 반환하는 메서드. 생성자와 같은 의미.
        --> 왜 쓸까? 생성 목적을 이름으로 명확하게 표현할 수 있다. 예: api에서는 성공했을 떄와 실패했을 때를 나누는 것처럼.

        호출 시. ApiResponse.sucess(...) 값 넣어서 호출.
     */

    public  static<T> ApiResponse<T> success(int status, String message, T data){
        return new ApiResponse<>(status, message, data);
    }

    public  static<T> ApiResponse<T> failure(int status, String message){
        return new ApiResponse<>(status, message, null);
    }



}
