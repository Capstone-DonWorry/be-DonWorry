package capstone.donworry.calendar.dto;

import lombok.Builder;

import java.util.List;

@Builder
public class MonthlyExpenseSummaryDto {

    public Long goalAmount;
    public Long totalExpenseAndExpectedExpense;
    public Long remaining;
    public Long cardExpenses;
    public Long cashExpenses;
    public List<DailySummaryDto> days;

    public MonthlyExpenseSummaryDto(Long goalAmount,
                                    Long totalExpenseAndExpectedExpense,
                                    Long remaining,
                                    Long cardExpenses,
                                    Long cashExpenses,
                                    List<DailySummaryDto> days) {
        this.goalAmount = goalAmount;
        this.totalExpenseAndExpectedExpense = totalExpenseAndExpectedExpense;
        this.remaining = remaining;
        this.cardExpenses = cardExpenses;
        this.cashExpenses = cashExpenses;
        this.days = days;
    }
}
