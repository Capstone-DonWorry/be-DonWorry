package capstone.donworry.statistics.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class WeeklyExpenseDTO {
    private int year;
    private int week;
    private Long totalExpense;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long dailyGoal;

    public WeeklyExpenseDTO(int year, int week, Long totalExpense,
                            LocalDate startDate, LocalDate endDate, Long dailyGoal) {
        this.year = year;
        this.week = week;
        this.totalExpense = totalExpense;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dailyGoal = dailyGoal;
    }
}
