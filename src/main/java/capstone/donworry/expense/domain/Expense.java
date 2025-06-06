package capstone.donworry.expense.domain;


import capstone.donworry.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expenseId", updatable = false)
    private Long expenseId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "amount", nullable = false)
    private Long amount;

    @Column(name = "expenseDate", nullable = false)
    private LocalDate expenseDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private ExpenseCategory category;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment", nullable = false)
    private PaymentMethod payment;

    @Column(name = "note")
    private String note;

    @Column(name = "bankName")
    private String bankName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public Expense(String title, Long amount, LocalDate expenseDate, ExpenseCategory category, PaymentMethod payment, String note, String bankName, Member member) {
        this.title = title;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.category = category;
        this.payment = payment;
        this.note = note;
        this.bankName = bankName;
        this.member = member;
    }

    public void update(String title, Long amount, LocalDate expenseDate, ExpenseCategory category, PaymentMethod payment, String note, String bankName, Member member) {
        this.title = title;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.category = category;
        this.payment = payment;
        this.note = note;
        this.bankName = bankName;
        this.member = member;
    }
}
