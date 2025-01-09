package controller;

/**
 * class         : ErrorResponse
 * date          : 25-01-08
 * description   : 에러 응답 메시지를 담는 객체로, 클라이언트에게 에러 정보를 전달하기 위해 사용.
 */
public class ErrorResponse {
    private String message;

    /**
     * constructor   : ErrorResponse
     * date          : 25-01-08
     * description   : 지정된 에러 메시지로 ErrorResponse 객체를 생성.
     */
    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
