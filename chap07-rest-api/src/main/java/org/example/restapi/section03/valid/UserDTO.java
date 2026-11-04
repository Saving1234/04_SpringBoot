package org.example.restapi.section03.valid;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

//validation
/*
    스프링부트 벨리데이션.

    사용자가 보낸 데이터가 !!정해진 조건!!을, 만족하는지 검사하는 라이브러리.

 */

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
@ToString

public class UserDTO {



    private int no;
    //!!!위에서부터 순서대로 처리. 위가 통과되면 아래 제약 검증.!!

    //아이디 위에 있기 때문에, 아이디에만 적용.
    @NotNull(message = "아이디는 반드시 입력해야합니다.")
    @NotBlank(message = "아이디는  공백만 있으면 안됨.")
    @Size(min = 8, max = 20, message = "아이디는 8자 이상, 20자 이하로 입력해야합니다.")  //아이디 길이 제약
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "아이디는 영문자, 숫자, 밑줄만 허용됩니다.")  //아이디는 소문자 a~대문자 z까지.
    //정규표현식. 인터넷에 찾아보면서. //프론트엔드에서 아이디 검증할 때도 사용. 지금도 아이디 검증.
    //제약을 거는 방식인가?
    private String id;

    //password는 8자 이상, 30자 이하로 입력.
    @Size(min = 8,max = 30, message = "비밀번호는 8자 이상, 30자 이하로 입력해야합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "비밀번호는 ")
    private String pw;
    @NotNull(message = "이름은 반드시 입력해야합니다")
    @NotBlank(message = "이름은 공백만 있으면 안됨")
    private String name;

    @Past(message = "가입일은 과거 날짜만 입력할 수 있습니다.") //past. 과거만 할 수 있게
//    @Future(message = "현재보다 미래")
//    @PastOrPresent(message = "과거 또는 현재")
//    @FutureOrPresent(message = "현재 또는 미래")
    private Date enrollAT;

    public UserDTO(String id, String pw, String name) {
        this.id = id;
        this.pw = pw;
        this.name = name;
    }



}
