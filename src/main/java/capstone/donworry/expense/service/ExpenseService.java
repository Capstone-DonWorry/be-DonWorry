package capstone.donworry.expense.service;

import capstone.donworry.expense.dto.ExpenseRequestDTO;
import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.repository.ExpenseRepository;
import capstone.donworry.member.domain.Member;
import capstone.donworry.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final MemberRepository memberRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id, Long memberId){
        return expenseRepository.findByExpenseIdAndMemberId(id, memberId).
                orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터를 찾을 수 없습니다."));
    }

    public Long addExpense(ExpenseRequestDTO expenseRequestDTO, Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
        Expense expense = expenseRequestDTO.toEntity(member);
        Expense savedExpense =  expenseRepository.save(expense);

        return savedExpense.getExpenseId();
    }

    public void deleteExpense(Long id, Long memberId) {
        Expense expense = expenseRepository.findByExpenseIdAndMemberId(id, memberId)
                        .orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터를 찾을 수 없습니다."));
        expenseRepository.delete(expense);
    }

    @Transactional
    public Expense updateExpense(Long id, Long memberId, ExpenseRequestDTO expenseRequestDTO){
        Expense savedExpense = expenseRepository.findByExpenseIdAndMemberId(id, memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터를 찾을 수 없습니다."));
        savedExpense.update(expenseRequestDTO.getTitle(),
                expenseRequestDTO.getAmount(),
                expenseRequestDTO.getExpenseDate(),
                expenseRequestDTO.getCategory(),
                expenseRequestDTO.getPayment(),
                expenseRequestDTO.getNote(),
                savedExpense.getMember());

        return savedExpense;
    }
}