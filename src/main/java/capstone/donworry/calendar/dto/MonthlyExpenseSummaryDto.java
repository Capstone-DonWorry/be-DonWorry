package capstone.donworry.calendar.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Builder
public class MonthlyExpenseSummaryDto {

    public Long goalAmount;
    public Long totalExpenseAndExpectedExpense;
    public Long remaining;
    public Long cardExpenses;
    public Long cashExpenses;
    public Map<LocalDate, DailySummaryDto> days;

    public MonthlyExpenseSummaryDto(Long goalAmount,
                                    Long totalExpenseAndExpectedExpense,
                                    Long remaining,
                                    Long cardExpenses,
                                    Long cashExpenses,
                                    Map<LocalDate, DailySummaryDto> days) {
        this.goalAmount = goalAmount;
        this.totalExpenseAndExpectedExpense = totalExpenseAndExpectedExpense;
        this.remaining = remaining;
        if(this.remaining < 0) this.remaining = 0L;
        this.cardExpenses = cardExpenses;
        this.cashExpenses = cashExpenses;
        this.days = days;
    }
}
