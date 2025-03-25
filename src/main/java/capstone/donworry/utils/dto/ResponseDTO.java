package capstone.donworry.utils.dto;

import org.springframework.http.HttpStatus;

public class ResponseDTO {

    private HttpStatus code;

    private String message;

    public ResponseDTO(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

}
