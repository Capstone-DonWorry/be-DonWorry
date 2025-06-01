package capstone.donworry.monthlyExpenseGoals.domain;

import capstone.donworry.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "monthly_expense_goal",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "year", "month"}))
@NoArgsConstructor
@Getter
public class MonthlyExpenseGoal {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monthlyGoals_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private int year;

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
