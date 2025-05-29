package capstone.donworry.calendar.dto;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expense.domain.Expense;

import java.time.LocalDate;
import java.util.List;

public class DailyExpenseSummaryDto {
    private LocalDate date;
    private List<Expense> expenseList;
    private List<ExpectedExpenditure> expectedExpenditureList;

    public DailyExpenseSummaryDto(LocalDate date,
                                  List<Expense> expenseList,
                                  List<ExpectedExpenditure> expectedExpenditureList) {
        this.date = date;
        this.expenseList = expenseList;
        this.expectedExpenditureList = expectedExpenditureList;
    }
}
