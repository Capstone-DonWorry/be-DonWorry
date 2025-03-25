package capstone.donworry.utils.dto;

import lombok.Getter;

@Getter
public class DataResponseDTO<T> extends ResponseDTO {

    private final T data;

    private DataResponseDTO(T data) {
        super(ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }

}
