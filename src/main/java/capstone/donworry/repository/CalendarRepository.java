package capstone.donworry.repository;

import capstone.donworry.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalendarRepository extends JpaRepository<Expense, Long> {
}
