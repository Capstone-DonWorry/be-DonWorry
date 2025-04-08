package capstone.donworry.expectedExpenditure.dto;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class ExpectedExpenditureRequestDTO {
    private String details;

    private Long amount;

    private LocalDate date;

    public ExpectedExpenditure toEntity() {
        return new ExpectedExpenditure(details, amount, date);
    }
}
