package capstone.donworry.expectedExpenditure.dto;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.member.domain.Member;

import java.time.LocalDate;

public class ExpectedExpenditureResponseDTO {

    private Long id;

    private String details;

    private Long amount;

    private LocalDate date;

    private Member member;

    private ExpectedExpenditureResponseDTO(Long id, String details, Long amount, LocalDate date, Member member) {
        this.id = id;
        this.details = details;
        this.amount = amount;
        this.date = date;
        this.member = member;
    }

    public static ExpectedExpenditureResponseDTO from(ExpectedExpenditure expectedExpenditure) {
        return new ExpectedExpenditureResponseDTO(
                expectedExpenditure.getExpectedExpenditureId(),
                expectedExpenditure.getDetails(),
                expectedExpenditure.getAmount(),
                expectedExpenditure.getDate(),
                expectedExpenditure.getMember());
    }
}
