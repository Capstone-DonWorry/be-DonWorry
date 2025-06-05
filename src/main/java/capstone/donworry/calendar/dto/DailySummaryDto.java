package capstone.donworry.calendar.dto;

public class DailySummaryDto {
    public Long dailyTotalExpense;
    public Long dailyTotalExpectedExpense;
    public Long dailyGoal;

    public DailySummaryDto(Long dailyTotalExpense, Long dailyTotalExpectedExpense, Long dailyGoal) {
        this.dailyTotalExpense = dailyTotalExpense;
        this.dailyTotalExpectedExpense = dailyTotalExpectedExpense;
        this.dailyGoal = dailyGoal;
    }
}
