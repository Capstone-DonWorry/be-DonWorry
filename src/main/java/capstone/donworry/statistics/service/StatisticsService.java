package capstone.donworry.statistics.service;

import capstone.donworry.member.domain.Member;
import capstone.donworry.member.repository.MemberRepository;
import capstone.donworry.statistics.dto.DailyExpenseDTO;
import capstone.donworry.statistics.dto.PaymentExpenseDTO;
import capstone.donworry.statistics.repository.ExpenseStatisticsRepository;
import capstone.donworry.statistics.dto.WeeklyExpenseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final ExpenseStatisticsRepository expenseStatisticsRepository;
    private final MemberRepository memberRepository;

    public List<WeeklyExpenseDTO> getWeeklyExpense(Long memberId, LocalDate startDate, LocalDate endDate) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원 정보를 찾을 수 없습니다."));

        Long goalAmount = member.getGoalAmount();
        int daysInMonth = startDate.lengthOfMonth();
        long dailyGoal = goalAmount / daysInMonth;

        List<Object[]> result = expenseStatisticsRepository.findWeeklyExpense(memberId, startDate, endDate);

        return result.stream()
                .map(row -> {
                    int year = (Integer) row[0];
                    int week = (Integer) row[1];
                    Long totalSpent = (Long) row[2];

                    LocalDate weekStart = LocalDate
                            .now()
                            .withYear(year)
                            .with(WeekFields.ISO.weekOfYear(), week)
                            .with(WeekFields.ISO.dayOfWeek(), 1);
                    LocalDate weekEnd = weekStart.plusDays(6);

                    return new WeeklyExpenseDTO(
                            year, week, totalSpent, weekStart, weekEnd, goalAmount);
                })
                .collect(Collectors.toList());
    }

    public List<DailyExpenseDTO> getDailyExpense(Long memberId, LocalDate startDate, LocalDate endDate){
        if(!memberRepository.existsById(memberId)){
            throw new EntityNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        List<Object[]> result = expenseStatisticsRepository.findDailyExpense(memberId, startDate, endDate);

        return result.stream()
                .map(row -> new DailyExpenseDTO(
                        (LocalDate) row[0],
                        (Long) row[1]
                ))
                .collect(Collectors.toList());
    }

    public List<PaymentExpenseDTO> getWeeklyPaymentExpense(Long memberId, LocalDate startDate, LocalDate endDate) {
        if (!memberRepository.existsById(memberId)) {
            throw new EntityNotFoundException("회원 정보를 찾을 수 없습니다.");
        }

        List<Object[]> result = expenseStatisticsRepository.findWeeklyExpenseByPaymentMethod(memberId, startDate, endDate);

        return result.stream()
                .map(row -> new PaymentExpenseDTO(
                        (String) row[0],
                        (Long) row[1]
                ))
                .collect(Collectors.toList());
    }

}
