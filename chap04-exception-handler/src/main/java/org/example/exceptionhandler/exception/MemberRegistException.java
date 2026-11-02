package org.example.exceptionhandler.exception;

import org.springframework.stereotype.Controller;


public class MemberRegistException extends Exception {

    //부모로 부터 예외 메시지 저장.
    // CONSTRUCTOR로.
    public MemberRegistException(String message) {
        super(message);  //부모격, 최상위부모 THROWABLE detailMessage에 메시지를 넣는다.
    }
}
