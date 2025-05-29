package capstone.donworry.statistics.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentExpenseDTO {
    private String paymentMethod;
    private Long totalAmount;

    public PaymentExpenseDTO(String paymentMethod, Long totalAmount) {
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
    }
}
