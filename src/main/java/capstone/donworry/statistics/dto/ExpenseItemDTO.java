package capstone.donworry.statistics.dto;

import capstone.donworry.expense.domain.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ExpenseItemDTO {
    private Long id;
    private String title;
    private Long amount;
    private PaymentMethod paymentMethod;
    private LocalDate date;
}
