package capstone.donworry.utils.dto;

public class ResponseDTO {

    private int code;

    private String message;

    public ResponseDTO(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
