package capstone.donworry.expense.controller;

import capstone.donworry.expectedExpenditure.dto.CreatedIdResponseDTO;
import capstone.donworry.expense.dto.ExpenseRequestDTO;
import capstone.donworry.expense.dto.ExpenseResponseDTO;
import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.service.ExpenseService;
import capstone.donworry.global.response.DataResponseDTO;
import capstone.donworry.oauth.kakao.repository.MemberRepository;
import capstone.donworry.oauth.kakao.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final MemberRepository memberRepository;


    @GetMapping
    public ResponseEntity<DataResponseDTO<List<ExpenseResponseDTO>>> getAllExpenses(){
        List<Expense> expenses = expenseService.getAllExpenses();
        List<ExpenseResponseDTO> expenseResponseDTOList = expenses.stream()
                .map(ExpenseResponseDTO::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(DataResponseDTO.success(expenseResponseDTOList));
    }

    @GetMapping("{id}")
    public ResponseEntity<DataResponseDTO<ExpenseResponseDTO>> getExpenseById(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails){

        Long memberId = userDetails.getMemberId();
        Expense expense = expenseService.getExpenseById(id, memberId);
        ExpenseResponseDTO expenseResponseDTO = ExpenseResponseDTO.from(expense);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(expenseResponseDTO));
    }


    @PostMapping()
    public ResponseEntity<DataResponseDTO<CreatedIdResponseDTO>> addExpense(
            @RequestBody ExpenseRequestDTO expenseRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails){

        Long memberId = userDetails.getMemberId();
        Long savedId = expenseService.addExpense(expenseRequestDTO, memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(new CreatedIdResponseDTO(savedId)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponseDTO<Void>> deleteExpense(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails){

        Long memberId = userDetails.getMemberId();
        expenseService.deleteExpense(id, memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(null));
    }


    @PutMapping("/{id}")
    public ResponseEntity<DataResponseDTO<Expense>> updateExpense(
            @PathVariable Long id,
            @RequestBody ExpenseRequestDTO expenseRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails){

        Long memberId = userDetails.getMemberId();
        Expense updateExpense = expenseService.updateExpense(id, memberId, expenseRequestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(updateExpense));
    }
}

