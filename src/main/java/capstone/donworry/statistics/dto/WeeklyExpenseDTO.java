package capstone.donworry.statistics.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class WeeklyExpenseDTO {
    private int year;
    private int week;
    private Long totalSpent;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long dailyGoal;

    public WeeklyExpenseDTO(int year, int week, Long totalSpent,
                            LocalDate startDate, LocalDate endDate, Long dailyGoal) {
        this.year = year;
        this.week = week;
        this.totalSpent = totalSpent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dailyGoal = dailyGoal;
    }
}
