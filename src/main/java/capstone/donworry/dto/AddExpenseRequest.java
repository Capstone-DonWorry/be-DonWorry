package capstone.donworry.dto;

import capstone.donworry.domain.Expense;
import capstone.donworry.domain.ExpenseCategory;
import capstone.donworry.domain.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddExpenseRequest {

    private String title;
    private Long amount;
    private LocalDate expenseDate;
    private ExpenseCategory category;
    private PaymentMethod payment;
//    private User user;

    public Expense toEntity() {
        return Expense.builder()
                .title(title)
                .amount(amount)
                .expenseDate(expenseDate)
                .category(category)
                .payment(payment)
//                .user(user)
                .build();
    }
}
