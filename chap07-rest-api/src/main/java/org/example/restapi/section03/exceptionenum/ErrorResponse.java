package org.example.restapi.section03.exceptionenum;

//예외 발생했을 때 클라이언트에 전달할 응답 dto
public class ErrorResponse {


    private int status;
    private String code;
    private String message;
    private String detail;

    public ErrorResponse(int status, String code, String message, String detail) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public static ErrorResponse from(BusinesException exception){
        ErrorCode errorCode = exception.getErrorCode();

        return new ErrorResponse(
                errorCode.getHtp().value(),
                errorCode.getCode(),
                errorCode.getMessage(),
                exception.getMessage()

        );

    }
    public int getStatus(){
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDetail() {
        return detail;
    }
}
