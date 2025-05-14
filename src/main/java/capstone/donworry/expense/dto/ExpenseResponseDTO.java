package capstone.donworry.expense.dto;

import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.domain.ExpenseCategory;
import capstone.donworry.expense.domain.PaymentMethod;
import lombok.Getter;

import java.time.LocalDate;


public class ExpenseResponseDTO {

    private String title;
    private Long amount;
    private LocalDate expenseDate;
    private ExpenseCategory category;
    private PaymentMethod payment;

    private ExpenseResponseDTO(String title, Long amount, LocalDate expenseDate,
                               ExpenseCategory category, PaymentMethod payment) {
        this.title = title;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.category = category;
        this.payment = payment;
    }

    public static ExpenseResponseDTO from(Expense expense) {
        return new ExpenseResponseDTO(
                expense.getTitle(),
                expense.getAmount(),
                expense.getExpenseDate(),
                expense.getCategory(),
                expense.getPayment()
        );
    }
}
