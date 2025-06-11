package capstone.donworry.statistics.repository;

import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.domain.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseStatisticsRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT FUNCTION('YEARWEEK', e.expenseDate, 1) AS yearWeek, SUM(e.amount) " +
            "FROM Expense e " +
            "WHERE e.member.id = :memberId AND e.expenseDate BETWEEN :startDate AND :endDate " +
            "GROUP BY FUNCTION('YEARWEEK', e.expenseDate, 1) " +
            "ORDER BY yearWeek")
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
    List<Object[]> findExpenseByPaymentMethod(@Param("memberId") Long memberId,
                                                    @Param("startDate") LocalDate startDate,
                                                    @Param("endDate") LocalDate endDate);


    @Query("SELECT e.category, SUM(e.amount) " +
            "FROM Expense e " +
            "WHERE e.member.id = :memberId AND e.expenseDate BETWEEN :startDate AND :endDate " +
            "GROUP BY e.category " +
            "ORDER BY SUM(e.amount) DESC")
    List<Object[]> findMonthlyCategoryExpense(@Param("memberId") Long memberId,
                                              @Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(e.amount) " +
            "FROM Expense e " +
            "WHERE e.member.id = :memberId " +
            "AND e.expenseDate BETWEEN :startDate AND :endDate")
    Long findMonthlyTotalExpense(@Param("memberId") Long memberId,
                                 @Param("startDate") LocalDate startDate,
                                 @Param("endDate") LocalDate endDate);



    @Query("""
    SELECT e.expenseId, e.title, e.amount, e.payment, e.expenseDate
    FROM Expense e
    WHERE e.member.id = :memberId
      AND e.category = :category
      AND e.expenseDate BETWEEN :startDate AND :endDate
    ORDER BY e.expenseDate DESC
""")
    List<Object[]> findExpensesByCategory(Long memberId, ExpenseCategory category, LocalDate startDate, LocalDate endDate);

    @Query("""
    SELECT e.payment, SUM(e.amount)
    FROM Expense e
    WHERE e.member.id = :memberId
      AND e.category = :category
      AND e.expenseDate BETWEEN :startDate AND :endDate
    GROUP BY e.payment
""")
    List<Object[]> findPaymentSummaryByCategory(Long memberId, ExpenseCategory category, LocalDate startDate, LocalDate endDate);

    // 목표지출금액 기준 사용자 그룹 평균 월 지출
    @Query("""
    SELECT AVG(e.totalAmount)
    FROM (
        SELECT e.member.id AS memberId, SUM(e.amount) AS totalAmount
        FROM Expense e
        WHERE e.expenseDate BETWEEN :startDate AND :endDate
          AND e.member.id IN (
            SELECT m.id
            FROM Member m JOIN MonthlyExpenseGoal g ON m.id = g.member.id
            WHERE g.year = :year AND g.month = :month AND g.goalAmount = :goalAmount
          )
        GROUP BY e.member.id
    ) e
""")
    Double findAvgMonthlyExpenseByGoalAmount(@Param("year") int year,
                                             @Param("month") int month,
                                             @Param("goalAmount") Long goalAmount,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    // 목표지출금액 기준 상위 3개 카테고리
    @Query("""
    SELECT e.category, SUM(e.amount) as totalAmount
    FROM Expense e
    WHERE e.expenseDate BETWEEN :startDate AND :endDate
      AND e.member.id IN (
        SELECT m.id
        FROM Member m JOIN MonthlyExpenseGoal g ON m.id = g.member.id
        WHERE g.year = :year AND g.month = :month AND g.goalAmount = :goalAmount
      )
    GROUP BY e.category
    ORDER BY totalAmount DESC
""")
    List<Object[]> findTop3CategoriesByGoalAmount(@Param("year") int year,
                                                  @Param("month") int month,
                                                  @Param("goalAmount") Long goalAmount,
                                                  @Param("startDate") LocalDate startDate,
                                                  @Param("endDate") LocalDate endDate,
                                                  Pageable pageable);


    // 연령대 기준 평균 월 지출
    @Query("""
    SELECT AVG(e.totalAmount)
    FROM (
        SELECT e.member.id AS memberId, SUM(e.amount) AS totalAmount
        FROM Expense e
        WHERE e.expenseDate BETWEEN :startDate AND :endDate
          AND e.member.ageGroup = :ageGroup
        GROUP BY e.member.id
    ) e
""")
    Double findAvgMonthlyExpenseByAgeGroup(@Param("ageGroup") String ageGroup,
                                           @Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);

    // 연령대 기준 상위 3개 카테고리
    @Query("""
    SELECT e.category, SUM(e.amount) as totalAmount
    FROM Expense e
    WHERE e.expenseDate BETWEEN :startDate AND :endDate
      AND e.member.ageGroup = :ageGroup
    GROUP BY e.category
    ORDER BY totalAmount DESC
""")
    List<Object[]> findTop3CategoriesByAgeGroup(@Param("ageGroup") String ageGroup,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate,
                                                Pageable pageable);

}

