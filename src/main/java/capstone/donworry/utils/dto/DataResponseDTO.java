package capstone.donworry.utils.dto;

import lombok.Getter;

@Getter
public class DataResponseDTO<T> extends ResponseDTO {

    private final T data;

    private DataResponseDTO(T data) {
        super(ErrorCode.OK.getStatus(),
                ErrorCode.OK.getHttpStatus(),
                ErrorCode.OK.getMessage());
        this.data = data;
    }

    private DataResponseDTO(String message, T data) {
        super(ErrorCode.OK.getStatus(),
                ErrorCode.OK.getHttpStatus(),
                message);
        this.data = data;
    }

    public static <T> DataResponseDTO<T> success(T data) {
        return new DataResponseDTO<>(data);
    }

    public static <T> DataResponseDTO<T> successWithMessage(String message, T data) {
        return new DataResponseDTO<>(message, data);
    }

}
