package capstone.donworry.calendar.service;

import capstone.donworry.expectedExpenditure.repository.ExpectedExpenditureRepository;
import capstone.donworry.expense.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final ExpenseRepository expenseRepository;
    private final ExpectedExpenditureRepository expectedExpenditureRepository;

    //모든 년/월/id에 해당하는 지출 찾기 + 모든 예상 지출 검색

}
