package capstone.donworry.global.exception;

import capstone.donworry.global.response.ErrorCode;
import capstone.donworry.global.response.ErrorResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponseDTO> handlerEntityNotFoundException(EntityNotFoundException e) {
        log.error(e.toString(), e);
        return handlerException(ErrorCode.ENTITY_NOT_FOUND, e);
    }

    private ResponseEntity<ErrorResponseDTO> handlerException(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponseDTO.from(errorCode));
    }

    private ResponseEntity<ErrorResponseDTO> handlerException(ErrorCode errorCode, Exception e) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ErrorResponseDTO.of(errorCode, e));
    }

}
