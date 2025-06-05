package capstone.donworry.monthlyExpenseGoals.repository;

import capstone.donworry.monthlyExpenseGoals.domain.MonthlyExpenseGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonthlyExpenseGoalRepository extends JpaRepository<MonthlyExpenseGoal, Long> {

    Optional<MonthlyExpenseGoal> findByMemberIdAndYearAndMonth(Long memberId, int year, int month);

    Optional<MonthlyExpenseGoal> findFirstByMemberIdOrderByYearDescMonthDesc(Long memberId);

}
