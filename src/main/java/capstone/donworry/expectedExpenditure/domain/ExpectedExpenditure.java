package capstone.donworry.expectedExpenditure.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor
public class ExpectedExpenditure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String details;

    private Long amount;

    private LocalDate date;

    public ExpectedExpenditure(String details, Long amount, LocalDate date) {
        this.details = details;
        this.amount = amount;
        this.date = date;
    }

    public void update(String details, Long amount, LocalDate date) {
        this.details = details;
        this.amount = amount;
        this.date = date;
    }
}
