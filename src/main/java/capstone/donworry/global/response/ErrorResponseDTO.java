package capstone.donworry.global.response;

public class ErrorResponseDTO extends ResponseDTO {

    private ErrorResponseDTO(ErrorCode errorCode) {
        super(errorCode.getStatus(),
                errorCode.getHttpStatus(),
                errorCode.getMessage());
    }

    private ErrorResponseDTO(ErrorCode errorCode, Exception e) {
        super(errorCode.getStatus(),
                errorCode.getHttpStatus(),
                e.getMessage());
    }

    public static ErrorResponseDTO from(ErrorCode errorCode) {
        return new ErrorResponseDTO(errorCode);
    }

    public static ErrorResponseDTO of(ErrorCode errorCode, Exception e) {
        return new ErrorResponseDTO(errorCode, e);
    }
}
