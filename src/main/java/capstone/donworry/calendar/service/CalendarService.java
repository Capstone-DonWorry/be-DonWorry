package capstone.donworry.calendar.service;

import capstone.donworry.calendar.dto.DailyExpenseSummaryDto;
import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expectedExpenditure.repository.ExpectedExpenditureRepository;
import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final ExpenseRepository expenseRepository;
    private final ExpectedExpenditureRepository expectedExpenditureRepository;

    public DailyExpenseSummaryDto getDailyExpenseSummary(
            LocalDate date,
            Long memberId){

        List<Expense> expenses = expenseRepository
                .findAllByMemberIdAndExpenseDateBetween(memberId, date);
        List<ExpectedExpenditure> expectedExpenditures = expectedExpenditureRepository
                .findByMemberIdAndDateBetween(memberId, date);

        return new DailyExpenseSummaryDto(date, expenses, expectedExpenditures);
    }
}
