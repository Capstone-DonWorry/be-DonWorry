package capstone.donworry.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class WeeklyDetailExpenseDTO {
    private List<DailyExpenseDTO> dailyExpenses;
    private List<PaymentExpenseDTO> paymentExpenses;

    public WeeklyDetailExpenseDTO(List<DailyExpenseDTO> dailyExpenses, List<PaymentExpenseDTO> paymentExpenses){
        this.dailyExpenses = dailyExpenses;
        this.paymentExpenses = paymentExpenses;
    }
}
