package capstone.donworry.utils.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    OK(200 , HttpStatus.OK, "OK");

    private final int code;
    private final HttpStatus httpStatus;
    private final String message;

}
