package capstone.donworry.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static capstone.donworry.global.response.ResponseStatus.STATUS_FAIL;
import static capstone.donworry.global.response.ResponseStatus.STATUS_SUCCESS;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    //성공 response
    OK(STATUS_SUCCESS, HttpStatus.OK, "OK"),

    //실패 response
    INVALID_REQUEST(STATUS_FAIL, HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    ENTITY_NOT_FOUND(STATUS_FAIL, HttpStatus.NOT_FOUND , "해당 데이터를 찾을 수 없습니다");

    private final String status;
    private final HttpStatus httpStatus;
    private final String message;

}
