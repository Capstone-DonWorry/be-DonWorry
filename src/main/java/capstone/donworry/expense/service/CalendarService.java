package capstone.donworry.expense.service;

import capstone.donworry.expense.dto.AddExpenseRequest;
import capstone.donworry.expense.dto.UpdateExpenseRequest;
import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.repository.CalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CalendarService {

    private final CalendarRepository calendarRepository;

    public Expense add(AddExpenseRequest request){
        return calendarRepository.save(request.toEntity());
    }

    public void delete(long id) {
        calendarRepository.deleteById(id);
    }

    @Transactional
    public Expense update(long id, UpdateExpenseRequest request){
        Expense expense = calendarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
        expense.update(request.getTitle(), request.getAmount(), request.getExpenseDate(), request.getCategory(), request.getPayment());

        return expense;
    }
}