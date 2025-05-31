package capstone.donworry.expectedExpenditure.dto;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.member.domain.Member;
import lombok.Getter;
import java.time.LocalDate;

@Getter
public class ExpectedExpenditureRequestDTO {
    private String details;

    private Long amount;

    private LocalDate date;

    public ExpectedExpenditure toEntity(Member member) {
        return new ExpectedExpenditure(details, amount, date, member);
    }
}
