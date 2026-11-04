package org.example.restapi.section03.exceptionenum;

public class BusinesException extends RuntimeException {

        private final ErrorCode errorCode;



    public BusinesException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BusinesException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
