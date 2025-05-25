package capstone.donworry.expense.repository;

import capstone.donworry.expense.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    Optional<Expense> findByExpenseIdAndMemberId(Long expenseId, Long memberId);

}
