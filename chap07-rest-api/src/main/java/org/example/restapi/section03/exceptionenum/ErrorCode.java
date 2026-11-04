package org.example.restapi.section03.exceptionenum;

import org.springframework.http.HttpStatus;

//어플리케이션에서 사용할 예외 정보를 관리하는 Enum
//예외를 정의, 예외 메시지를 일관성있게 전달하는 목적.
public enum ErrorCode {

    //추후 필요한 예외가 있으면 내가 정의해서 추가하면 됨.
    //이름 정하고, CODE. CODE와 이름이 일치하지 않아도 되지만 잘 알 수있게.

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "회원을 찾을 수 없습니다"),
    INVALID_USER_NUMBER(HttpStatus.BAD_REQUEST, "INVALID_USER_NUMBER", "이미 사용중인 회원 이름입니다"),
    DUPLICATE_USER_NAME(HttpStatus.CONFLICT, "DUPLICATE_USER_NAME", "이미 사용중인 회원 이름입니다");

    private final HttpStatus htp;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus htp, String code, String message) {
        this.htp = htp;
        this.code = code;
        this.message = message;
    }



    public HttpStatus getHtp() {
        return htp;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
