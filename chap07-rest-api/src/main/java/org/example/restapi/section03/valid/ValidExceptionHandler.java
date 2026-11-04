package org.example.restapi.section03.valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

//예외 만들고ㅓ.
@RestControllerAdvice(basePackages = "org.example.restapi.section03.valid")
public class ValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)  //스프링 내, exception.
    public ResponseEntity<Map<String, String>>handleValidationException(MethodArgumentNotValidException exception){

        //검증오류객체.
        //특정 필드에서 발생한 검증오류정보를 담은 객체
        FieldError fieldError = exception.getBindingResult().getFieldError();

        Map<String, String> response = Map.of("field", fieldError.getField(), "message", fieldError.getDefaultMessage());
        //getField : fieldError의 문제.
        return ResponseEntity.badRequest().body(response);


    }



}
