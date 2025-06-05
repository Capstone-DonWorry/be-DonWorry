package capstone.donworry.monthlyExpenseGoals.service;

import capstone.donworry.member.domain.Member;
import capstone.donworry.member.repository.MemberRepository;
import capstone.donworry.monthlyExpenseGoals.domain.MonthlyExpenseGoal;
import capstone.donworry.monthlyExpenseGoals.repository.MonthlyExpenseGoalRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MonthlyExpenseGoalService {

    private final MonthlyExpenseGoalRepository monthlyExpenseGoalRepository;
    private final MemberRepository memberRepository;

    //이번 달 목표 소비 금액 조회
    public MonthlyExpenseGoal getMonthGoal(Long memberId, int year, int month, LocalDate today) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        //해당 달 목표 소비 금액 조회
        Optional<MonthlyExpenseGoal> currentGoalOpt = monthlyExpenseGoalRepository.
                findByMemberIdAndYearAndMonth(memberId, year, month);

        if (currentGoalOpt.isPresent()) {
            return currentGoalOpt.get();
        }

        //없다면 최신 목표 소비 금액 조회
        Optional<MonthlyExpenseGoal> latestGoalOpt = monthlyExpenseGoalRepository
                .findFirstByMemberIdOrderByYearDescMonthDesc(memberId);

        if (latestGoalOpt.isEmpty()) {
            throw new EntityNotFoundException("설정된 목표 금액이 없습니다.");
        }

        MonthlyExpenseGoal monthlyGoal = new MonthlyExpenseGoal(
                member, year, month, latestGoalOpt.get().getGoalAmount());

        LocalDate targetDate = LocalDate.of(year, month, 1);
        LocalDate currentMonth = LocalDate.of(today.getYear(), today.getMonthValue(), 1);

        //과거나 현재라면 저장 후 반환(값이 없을 시 DB 여러 번 조회, 그 뒤에는 한 번 조회)
        if(!targetDate.isAfter(currentMonth)) {
            return monthlyExpenseGoalRepository.save(monthlyGoal);
        } else { //미래면 -> 저장하지 않고 반환
            return monthlyGoal;
        }
    }
}
