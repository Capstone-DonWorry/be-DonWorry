package capstone.donworry.expense.domain;

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

//    @ManyToOne(fetch = FetchType.LAZY)  // 사용자:지출=n:1
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @Builder
    public Expense(String title, Long amount, LocalDate expenseDate, ExpenseCategory category, PaymentMethod payment) {
        this.title = title;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.category = category;
        this.payment = payment;
//        this.user = user;
    }

    public void update(String title, Long amount, LocalDate expenseDate, ExpenseCategory category, PaymentMethod payment) {
        this.title = title;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.category = category;
        this.payment = payment;
//        this.user = user;
    }
}
