package capstone.donworry.expectedExpenditure.dto;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;

import java.time.LocalDate;

public class ExpectedExpenditureResponseDTO {

    private String details;

    private Long amount;

    private LocalDate date;

    private ExpectedExpenditureResponseDTO(String details, Long amount, LocalDate date) {
        this.details = details;
        this.amount = amount;
        this.date = date;
    }

    public static ExpectedExpenditureResponseDTO from(ExpectedExpenditure expectedExpenditure) {
        return new ExpectedExpenditureResponseDTO(
                expectedExpenditure.getDetails(),
                expectedExpenditure.getAmount(),
                expectedExpenditure.getDate()
        );
    }

}
