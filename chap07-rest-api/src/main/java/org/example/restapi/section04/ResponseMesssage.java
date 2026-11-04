package org.example.restapi.section04;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

//응답 메시지에 관한 DTO?
public class ResponseMesssage {

    private int httpStatus; //HTTP 응답코드
    private String message; //HTTP 응답메시지
    private Map<String, Object> result; //HTTP 값.
}
