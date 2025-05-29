package capstone.donworry.statistics.repository;

import capstone.donworry.expense.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseStatisticsRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT FUNCTION('YEAR', e.expenseDate) AS year, FUNCTION('WEEK', e.expenseDate) AS week, SUM(e.amount) " +
            "FROM Expense e " +
            "WHERE e.member.id = :memberId AND e.expenseDate BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('YEAR', e.expenseDate), FUNCTION('WEEK', e.expenseDate) " +
            "ORDER BY year DESC, week")
    List<Object[]> findWeeklyExpense(@Param("memberId") Long memberId,
                                      @Param("startDate") LocalDate startDate,
                                      @Param("endDate") LocalDate endDate);

    @Query("SELECT e.expenseDate, SUM(e.amount) " +
            "FROM Expense e " +
            "WHERE e.member.id = :memberId " +
            "AND e.expenseDate BETWEEN :startDate AND :endDate " +
            "GROUP BY e.expenseDate " +
            "ORDER BY e.expenseDate")
    List<Object[]> findDailyExpense(@Param("memberId") Long memberId,
                                     @Param("startDate") LocalDate startDate,
                                     @Param("endDate") LocalDate endDate);


    @Query("SELECT e.payment, SUM(e.amount) " +
            "FROM Expense e " +
            "WHERE e.member.id = :memberId " +
            "AND e.expenseDate BETWEEN :startDate AND :endDate " +
            "GROUP BY e.payment")
    List<Object[]> findWeeklyExpenseByPaymentMethod(@Param("memberId") Long memberId,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);

}

