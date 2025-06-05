package capstone.donworry.statistics.service;

import capstone.donworry.expense.domain.ExpenseCategory;
import capstone.donworry.expense.domain.PaymentMethod;
import capstone.donworry.member.repository.MemberRepository;
import capstone.donworry.monthlyExpenseGoals.domain.MonthlyExpenseGoal;
import capstone.donworry.monthlyExpenseGoals.service.MonthlyExpenseGoalService;
import capstone.donworry.statistics.dto.*;
import capstone.donworry.statistics.repository.ExpenseStatisticsRepository;
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
    private final MonthlyExpenseGoalService monthlyExpenseGoalService;

    // 한달간 주간 소비 요약
    public List<WeeklyExpenseDTO> getWeeklyExpense(Long memberId, LocalDate startDate, LocalDate endDate) {

        MonthlyExpenseGoal monthlyExpenseGoal = monthlyExpenseGoalService
                .getMonthGoal(memberId, startDate.getYear(), startDate.getMonthValue(), LocalDate.now());
        Long goalAmount = monthlyExpenseGoal.getGoalAmount();
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
                            year, week, totalSpent, weekStart, weekEnd, dailyGoal);
                })
                .collect(Collectors.toList());
    }

    // 주간 소비 세부사항
    public WeeklyDetailExpenseDTO getWeeklyDetailStatistics(Long memberId, LocalDate startDate, LocalDate endDate) {
        if (!memberRepository.existsById(memberId)) {
            throw new EntityNotFoundException("회원 정보를 찾을 수 없습니다.");
        }


        List<Object[]> dailyResult = expenseStatisticsRepository.findDailyExpense(memberId, startDate, endDate);
        List<DailyExpenseDTO> dailyExpenses = dailyResult.stream()
                .map(row -> new DailyExpenseDTO((LocalDate) row[0], (Long) row[1]))
                .collect(Collectors.toList());


        List<Object[]> paymentResult = expenseStatisticsRepository.findExpenseByPaymentMethod(memberId, startDate, endDate);
        List<PaymentExpenseDTO> paymentExpenses = paymentResult.stream()
                .map(row -> new PaymentExpenseDTO((PaymentMethod) row[0], (Long) row[1]))
                .collect(Collectors.toList());

        return new WeeklyDetailExpenseDTO(dailyExpenses, paymentExpenses);
    }



    public MonthlyStatisticsDTO getMonthlyCategoryStatistics(Long memberId, LocalDate startDate, LocalDate endDate) {

        Long totalExpense = expenseStatisticsRepository.findMonthlyTotalExpense(memberId, startDate, endDate);
        MonthlyExpenseGoal expenseGoal = monthlyExpenseGoalService
                .getMonthGoal(memberId, startDate.getYear(), startDate.getMonthValue(), LocalDate.now());
        Long goalAmount = expenseGoal.getGoalAmount();

        List<CategoryExpenseDTO> categoryExpenses = expenseStatisticsRepository
                .findMonthlyCategoryExpense(memberId, startDate, endDate)
                .stream()
                .map(row -> new CategoryExpenseDTO((ExpenseCategory) row[0], (Long) row[1]))
                .collect(Collectors.toList());

        List<PaymentExpenseDTO> paymentExpenses = expenseStatisticsRepository
                .findExpenseByPaymentMethod(memberId, startDate, endDate)
                .stream()
                .map(row -> new PaymentExpenseDTO((PaymentMethod) row[0], (Long) row[1]))
                .collect(Collectors.toList());

        return new MonthlyStatisticsDTO(totalExpense, goalAmount, categoryExpenses, paymentExpenses);
    }

    public CategoryExpenseDetailDTO getCategoryExpenseDetail(Long memberId, ExpenseCategory category, LocalDate startDate, LocalDate endDate) {
        if (!memberRepository.existsById(memberId)) {
            throw new EntityNotFoundException("회원 정보를 찾을 수 없습니다.");
        }


        List<Object[]> expenseRows = expenseStatisticsRepository.findExpensesByCategory(memberId, category, startDate, endDate);
        List<ExpenseItemDTO> expenses = expenseRows.stream()
                .map(row -> new ExpenseItemDTO(
                        (Long) row[0],
                        (String) row[1],
                        (Long) row[2],
                        (PaymentMethod) row[3],
                        (LocalDate) row[4]
                ))
                .collect(Collectors.toList());


        List<Object[]> paymentRows = expenseStatisticsRepository.findPaymentSummaryByCategory(memberId, category, startDate, endDate);
        List<PaymentExpenseDTO> paymentExpenses = paymentRows.stream()
                .map(row -> new PaymentExpenseDTO(
                        (PaymentMethod) row[0],
                        (Long) row[1]
                ))
                .collect(Collectors.toList());

        return new CategoryExpenseDetailDTO(expenses, paymentExpenses);
    }


}
