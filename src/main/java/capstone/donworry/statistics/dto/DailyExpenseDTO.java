package capstone.donworry.statistics.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class DailyExpenseDTO {
    private LocalDate date;
    private Long amount;

    public DailyExpenseDTO(LocalDate date, Long amount) {
        this.date = date;
        this.amount = amount;
    }
}
