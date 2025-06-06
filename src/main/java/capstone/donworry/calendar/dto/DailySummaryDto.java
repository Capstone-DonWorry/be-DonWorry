package capstone.donworry.calendar.dto;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expense.domain.Expense;

import java.util.List;

public class DailySummaryDto {
    public Long dailyTotalExpense;
    public Long dailyTotalExpectedExpense;
    public Long dailyGoal;
    public List<Expense> dailyExpenseList;
    public List<ExpectedExpenditure> dailyExpectedList;

    public DailySummaryDto(Long dailyTotalExpense,
                           Long dailyTotalExpectedExpense,
                           Long dailyGoal,
                           List<Expense> dailyExpenseList,
                           List<ExpectedExpenditure> dailyExpectedList) {
        this.dailyTotalExpense = dailyTotalExpense;
        this.dailyTotalExpectedExpense = dailyTotalExpectedExpense;
        this.dailyGoal = dailyGoal;
        this.dailyExpenseList = dailyExpenseList;
        this.dailyExpectedList = dailyExpectedList;
    }
}
