package capstone.donworry.expectedExpenditure.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreatedIdResponseDTO {
    private Long id;

    public CreatedIdResponseDTO(Long id) {
        this.id = id;
    }
}
