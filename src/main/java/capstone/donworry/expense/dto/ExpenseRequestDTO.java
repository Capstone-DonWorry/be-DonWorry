package capstone.donworry.expense.dto;

import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.domain.ExpenseCategory;
import capstone.donworry.expense.domain.PaymentMethod;
import capstone.donworry.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ExpenseRequestDTO {
    private String title;
    private Long amount;
    private LocalDate expenseDate;
    private ExpenseCategory category;
    private PaymentMethod payment;
    private String note;
    private Long memberId;


    public Expense toEntity(Member member) {
        return Expense.builder()
                .title(title)
                .amount(amount)
                .expenseDate(expenseDate)
                .category(category)
                .payment(payment)
                .note(note)
                .member(member)
                .build();
    }
}

