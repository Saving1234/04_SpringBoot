package org.example.restapi.section03.exceptionenum;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//공통으로사용하는 예외.
// issue나 pullRequest 시, 팀원에게 꼭 공유하고 만들어야 함.
//에러코드 수정 및 추가할 때. 프로젝트 시. 회의/이슈로 알릴 시.

//@RestControllerAdvice : 이 경로에서 예외 발생시 가져와서 실행시키겠다.
@RestControllerAdvice(basePackages = "org.example.restapi.section03.exceptionenum.EnumExceptionController")
public class EnumExceptionHandler {

    @ExceptionHandler(BusinesException.class) //문법.
    public ResponseEntity<ErrorResponse>handleBusinessException(BusinesException businessException){

        ErrorCode errorCode = businessException.getErrorCode();
        ErrorResponse response = ErrorResponse.from(businessException);

        return ResponseEntity.status(errorCode.getHtp())
                .body(response);
    }

}
