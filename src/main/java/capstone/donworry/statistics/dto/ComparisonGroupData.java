package capstone.donworry.statistics.dto;

import lombok.Data;

import java.util.List;

@Data
public class ComparisonGroupData {
    private Long avgExpense;
    private List<CategoryExpenseDTO> topCategories;

    public ComparisonGroupData(Long avgExpense, List<CategoryExpenseDTO> topCategories){
        this.avgExpense = avgExpense;
        this.topCategories = topCategories;
    }
}
