package capstone.donworry.expense.repository;

import capstone.donworry.expense.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findByExpenseIdAndMemberId(Long expenseId, Long memberId);

    @Query("SELECT e FROM Expense e WHERE e.member.id = :memberId AND e.expenseDate BETWEEN :startDate AND :endDate")
    List<Expense> findAllByMemberIdAndExpenseDateBetween(
            @Param("memberId") Long memberId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}