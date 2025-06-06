package capstone.donworry.expense.dto;

import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.domain.ExpenseCategory;
import capstone.donworry.expense.domain.PaymentMethod;
import capstone.donworry.member.domain.Member;
import capstone.donworry.member.dto.MemberInExpenseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ExpenseResponseDTO {

    private String title;
    private Long amount;
    private LocalDate expenseDate;
    private ExpenseCategory category;
    private PaymentMethod payment;
    private String note;
    private String bankName;
    private MemberInExpenseDTO member;


    private ExpenseResponseDTO(String title, Long amount, LocalDate expenseDate,
                               ExpenseCategory category, PaymentMethod payment, String note,
                               String bankName, MemberInExpenseDTO member) {
        this.title = title;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.category = category;
        this.payment = payment;
        this.note = note;
        this.bankName = bankName;
        this.member = member;
    }

    public static ExpenseResponseDTO from(Expense expense) {
        return new ExpenseResponseDTO(
                expense.getTitle(),
                expense.getAmount(),
                expense.getExpenseDate(),
                expense.getCategory(),
                expense.getPayment(),
                expense.getNote(),
                expense.getBankName(),
                MemberInExpenseDTO.from(expense.getMember())
        );
    }
}
