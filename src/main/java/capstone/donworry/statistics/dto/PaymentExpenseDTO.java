package capstone.donworry.statistics.dto;

import capstone.donworry.expense.domain.PaymentMethod;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentExpenseDTO {
    private PaymentMethod paymentMethod;
    private Long totalAmount;

    public PaymentExpenseDTO(PaymentMethod paymentMethod, Long totalAmount) {
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
    }
}
