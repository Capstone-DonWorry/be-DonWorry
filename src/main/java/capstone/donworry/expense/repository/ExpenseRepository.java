package capstone.donworry.expense.repository;

import capstone.donworry.expense.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
