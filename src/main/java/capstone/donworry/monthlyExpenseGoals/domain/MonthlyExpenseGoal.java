package capstone.donworry.monthlyExpenseGoals.domain;

import capstone.donworry.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "monthly_expense_goal",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "year_num", "month_num"}))
@NoArgsConstructor
@Getter
@Setter
public class MonthlyExpenseGoal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monthlyGoals_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "year_num")
    private int year;

    @Column(name = "month_num")
    private int month;

    private Long goalAmount;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public MonthlyExpenseGoal(Member member, int year, int month, Long goalAmount) {
        this.member = member;
        this.year = year;
        this.month = month;
        this.goalAmount = goalAmount;
    }
}
