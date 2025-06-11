package capstone.donworry.calendar.service;

import capstone.donworry.calendar.dto.DailyExpenseSummaryDto;
import capstone.donworry.calendar.dto.DailySummaryDto;
import capstone.donworry.calendar.dto.MonthlyExpenseSummaryDto;
import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expectedExpenditure.dto.ExpectedExpenditureResponseDTO;
import capstone.donworry.expectedExpenditure.repository.ExpectedExpenditureRepository;
import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.domain.ExpenseCategory;
import capstone.donworry.expense.domain.PaymentMethod;
import capstone.donworry.expense.dto.ExpenseResponseDTO;
import capstone.donworry.expense.repository.ExpenseRepository;
import capstone.donworry.monthlyExpenseGoals.domain.MonthlyExpenseGoal;
import capstone.donworry.monthlyExpenseGoals.repository.MonthlyExpenseGoalRepository;
import capstone.donworry.monthlyExpenseGoals.service.MonthlyExpenseGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final ExpenseRepository expenseRepository;
    private final ExpectedExpenditureRepository expectedExpenditureRepository;
    private final MonthlyExpenseGoalService monthlyExpenseGoalService;

    public DailyExpenseSummaryDto getDailyExpenseSummary(
            LocalDate date,
            Long memberId){

        List<Expense> expenses = expenseRepository
                .findAllByMemberIdAndExpenseDate(memberId, date);
        List<ExpectedExpenditure> expectedExpenditures = expectedExpenditureRepository
                .findByMemberIdAndDateBetween(memberId, date);

        return new DailyExpenseSummaryDto(date, expenses, expectedExpenditures);
    }

    public MonthlyExpenseSummaryDto getMonthlyExpenseSummary(
            int year,
            int month,
            Long memberId) {
        //월별 목표 금액 구하기
        MonthlyExpenseGoal monthGoal = monthlyExpenseGoalService.
                getMonthGoal(memberId, year, month, LocalDate.now());
        Long goalAmount = monthGoal.getGoalAmount();

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

        //일별 지출 금액과 월별 총 지출 금액 구하기
        List<Expense> expenses = expenseRepository.findByMemberIdAndExpenseDateBetween(memberId, startDate, endDate);
        Long totalExpense = expenses.stream().mapToLong(Expense::getAmount).sum();


        //일별 예상 지출 금액과 월별 총 예상 지출 금액 구하기
        List<ExpectedExpenditure> expectedExpenses = expectedExpenditureRepository.
                findByMemberIdAndExpectedExpenditureDateBetween(memberId, startDate, endDate);
        Long totalExpectedExpense = expectedExpenses.stream().mapToLong(ExpectedExpenditure::getAmount).sum();

        //카드 지출 내역 보내기
        long cardExpenseSum = expenses.stream().
                filter(e -> e.getPayment().equals(PaymentMethod.CARD))
                .mapToLong(Expense::getAmount).sum();

        //현금 지출 내역 구하기(현금 지출 내역 = 현금지출 + 예상지출내역)
        long cashExpenseSum = expenses.stream()
                .filter(e -> e.getPayment().equals(PaymentMethod.CASH))
                .mapToLong(Expense::getAmount).sum();

        //총지출+예상지출금액과 잔액 구하기;
        long totalExpenseAndExpectedExpense = totalExpense + totalExpectedExpense;
        long remaining = goalAmount - totalExpenseAndExpectedExpense;
        if(remaining < 0) remaining = 0;

        //날짜별 지출 금액, 예상 지출 금액, 예상 목표 금액 구하기
        YearMonth yearMonth = YearMonth.of(year, month);
        int daysInMonth = yearMonth.lengthOfMonth();
        Long dailyGoalTemp = (goalAmount - totalExpectedExpense)/ daysInMonth;
        if(dailyGoalTemp < 0) dailyGoalTemp = 0L;

        Map<LocalDate, DailySummaryDto> dailySummaries = new LinkedHashMap<>();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(year, month, day);

            //현재 날짜 지출 총 금액
            Long dailyTotalExpense = expenses.stream()
                    .filter(e -> e.getExpenseDate().isEqual(date))
                    .mapToLong(Expense::getAmount)
                    .sum();

            //현재 날짜 예상지출 총 금액
            Long dailyTotalExpectedExpense = expectedExpenses.stream()
                    .filter(e -> e.getDate().isEqual(date))
                    .mapToLong(ExpectedExpenditure::getAmount)
                    .sum();

            //목표 지출 금액
            Long dailyGoal = dailyGoalTemp + dailyTotalExpectedExpense;

            //지출 단계 표시(그라데이션)
            int dailyLevel = expenseLevel(dailyTotalExpense, dailyTotalExpectedExpense, dailyGoal);

            //현재 날짜에 있는 지출 리스트와 예상지출 리스트 보내기
            List<ExpenseResponseDTO> dailyExpenseList = expenses.stream()
                    .filter(e -> e.getExpenseDate().isEqual(date))
                    .map(ExpenseResponseDTO::from)
                    .toList();

            List<ExpectedExpenditureResponseDTO> dailyExpectedList = expectedExpenses.stream()
                    .filter(e -> e.getDate().isEqual(date))
                    .map(ExpectedExpenditureResponseDTO::from)
                    .toList();

            dailySummaries.put(date,
                    new DailySummaryDto(dailyTotalExpense,
                            dailyTotalExpectedExpense,
                            dailyGoal,
                            dailyLevel,
                            dailyExpenseList,
                            dailyExpectedList)
            );
        }

        return MonthlyExpenseSummaryDto.builder()
                .goalAmount(goalAmount)
                .totalExpenseAndExpectedExpense(totalExpenseAndExpectedExpense)
                .remaining(remaining)
                .cardExpenses(cardExpenseSum)
                .cashExpenses(cashExpenseSum)
                .days(dailySummaries)
                .build();
    }

    private int expenseLevel(Long dailyExpense, Long dailyExpectedExpense, Long dailyGoal){
        Long total = dailyExpense + dailyExpectedExpense;
        if(total <= dailyGoal) return 1;
        else if(total <= dailyGoal * 1.25) return 2;
        else if(total <= dailyGoal * 1.5) return 3;
        else if(total <= dailyGoal * 1.75) return 4;
        else if(total <= dailyGoal * 2.0) return 5;
        else return 6;
    }
}
