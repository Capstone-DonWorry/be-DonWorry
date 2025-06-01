package capstone.donworry.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class MonthlyStatisticsDTO {
    private Long totalSpent;
    private Long goalAmount;
    private List<CategoryExpenseDTO> categoryExpenses;
    private List<PaymentExpenseDTO> paymentMethodExpenses;
}

