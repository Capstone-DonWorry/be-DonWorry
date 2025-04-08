package capstone.donworry.utils.dto;

import org.springframework.http.HttpStatus;

public class ResponseDTO {

    private String status;

    private HttpStatus code;

    private String message;

    public ResponseDTO(String status, HttpStatus code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
