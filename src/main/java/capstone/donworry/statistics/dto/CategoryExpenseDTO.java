package capstone.donworry.statistics.dto;

import capstone.donworry.expense.domain.ExpenseCategory;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class CategoryExpenseDTO {
    private ExpenseCategory category;
    private Long totalAmount;
    public CategoryExpenseDTO(ExpenseCategory category, Long totalAmount) {
        this.category = category;
        this.totalAmount = totalAmount;
    }
}
