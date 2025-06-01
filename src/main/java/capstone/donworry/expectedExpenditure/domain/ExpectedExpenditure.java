package capstone.donworry.expectedExpenditure.domain;

import capstone.donworry.member.domain.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class ExpectedExpenditure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expectedExpenditureId", updatable = false)
    private Long expectedExpenditureId;

    private String details;

    private Long amount;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public ExpectedExpenditure(String details, Long amount, LocalDate date, Member member) {
        this.details = details;
        this.amount = amount;
        this.date = date;
        this.member = member;
    }

    public void update(String details, Long amount, LocalDate date) {
        this.details = details;
        this.amount = amount;
        this.date = date;
    }
}
