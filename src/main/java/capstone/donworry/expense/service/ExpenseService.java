package capstone.donworry.expense.service;

import capstone.donworry.expense.dto.ExpenseRequestDTO;
import capstone.donworry.expense.dto.UpdateExpenseRequest;
import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id){
        return expenseRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터를 찾을 수 없습니다."));
    }
    public Long addExpense(Expense expense){
        Expense savedExpense =  expenseRepository.save(expense);

        return savedExpense.getExpenseId();
    }

    public void deleteExpense(long id) {
        expenseRepository.deleteById(id);
    }

    @Transactional
    public Expense updateExpense(long id, ExpenseRequestDTO expenseRequestDTO){
        Expense savedExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터를 찾을 수 없습니다."));
        savedExpense.update(expenseRequestDTO.getTitle(),
                expenseRequestDTO.getAmount(),
                expenseRequestDTO.getExpenseDate(),
                expenseRequestDTO.getCategory(),
                expenseRequestDTO.getPayment());

        return savedExpense;
    }
}