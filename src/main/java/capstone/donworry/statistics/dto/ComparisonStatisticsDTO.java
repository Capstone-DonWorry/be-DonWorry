package capstone.donworry.statistics.dto;

import lombok.Data;

import java.util.List;

@Data
public class ComparisonStatisticsDTO {
    private Long myTotalExpense;
    private List<CategoryExpenseDTO> myTopCategories;

    private ComparisonGroupData byGoalAmount;
    private ComparisonGroupData byAgeGroup;

    private Long myMonthExpenseGoal;
    private String ageGroup;

    public ComparisonStatisticsDTO(Long myTotalExpense, List<CategoryExpenseDTO> myTopCategories,
                                   ComparisonGroupData byGoalAmount, ComparisonGroupData byAgeGroup,
                                   Long myMonthExpenseGoal, String ageGroup) {
        this.myTotalExpense = myTotalExpense;
        this.myTopCategories = myTopCategories;
        this.byGoalAmount = byGoalAmount;
        this.byAgeGroup = byAgeGroup;
        this.myMonthExpenseGoal = myMonthExpenseGoal;
        this.ageGroup = ageGroup;
    }

}

