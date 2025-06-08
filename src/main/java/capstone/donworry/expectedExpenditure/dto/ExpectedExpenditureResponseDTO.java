package capstone.donworry.expectedExpenditure.dto;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.member.domain.Member;
import capstone.donworry.member.dto.MemberInExpenseDTO;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ExpectedExpenditureResponseDTO {

    private Long id;

    private String details;

    private Long amount;

    private LocalDate date;

    private MemberInExpenseDTO memberInExpenseDTO;

    private ExpectedExpenditureResponseDTO(Long id, String details, Long amount, LocalDate date, MemberInExpenseDTO member) {
        this.id = id;
        this.details = details;
        this.amount = amount;
        this.date = date;
        this.memberInExpenseDTO = member;
    }

    public static ExpectedExpenditureResponseDTO from(ExpectedExpenditure expectedExpenditure) {
        return new ExpectedExpenditureResponseDTO(
                expectedExpenditure.getExpectedExpenditureId(),
                expectedExpenditure.getDetails(),
                expectedExpenditure.getAmount(),
                expectedExpenditure.getDate(),
                MemberInExpenseDTO.from(expectedExpenditure.getMember()));
    }
}
