package capstone.donworry.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CategoryExpenseDetailDTO {
    private List<ExpenseItemDTO> expenses;
    private List<PaymentExpenseDTO> paymentExpenses;
}
