package capstone.donworry.global.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class ResponseDTO {

    @JsonProperty("status")
    private final String status;

    @JsonProperty("code")
    private final HttpStatus code;

    @JsonProperty("message")
    private final String message;


    public ResponseDTO(String status, HttpStatus code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
