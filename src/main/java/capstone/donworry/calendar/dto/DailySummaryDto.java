package capstone.donworry.calendar.dto;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expectedExpenditure.dto.ExpectedExpenditureResponseDTO;
import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.dto.ExpenseResponseDTO;

import java.util.List;

public class DailySummaryDto {
    public Long dailyTotalExpense;
    public Long dailyTotalExpectedExpense;
    public Long dailyGoal;
    public int dailyLevel;
    public List<ExpenseResponseDTO> dailyExpenseList;
    public List<ExpectedExpenditureResponseDTO> dailyExpectedList;

    public DailySummaryDto(Long dailyTotalExpense,
                           Long dailyTotalExpectedExpense,
                           Long dailyGoal,
                           int dailyLevel,
                           List<ExpenseResponseDTO> dailyExpenseList,
                           List<ExpectedExpenditureResponseDTO> dailyExpectedList) {
        this.dailyTotalExpense = dailyTotalExpense;
        this.dailyTotalExpectedExpense = dailyTotalExpectedExpense;
        this.dailyGoal = dailyGoal;
        this.dailyLevel = dailyLevel;
        this.dailyExpenseList = dailyExpenseList;
        this.dailyExpectedList = dailyExpectedList;
    }
}
