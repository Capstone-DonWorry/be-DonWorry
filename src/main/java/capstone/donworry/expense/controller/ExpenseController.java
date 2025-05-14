package capstone.donworry.expense.controller;

import capstone.donworry.expectedExpenditure.dto.CreatedIdResponseDTO;
import capstone.donworry.expense.dto.ExpenseRequestDTO;
import capstone.donworry.expense.dto.ExpenseResponseDTO;
import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.service.ExpenseService;
import capstone.donworry.global.response.DataResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;


    @GetMapping
    public ResponseEntity<DataResponseDTO<List<ExpenseResponseDTO>>> getAllExpenses(){
        List<Expense> expenses = expenseService.getAllExpenses();
        List<ExpenseResponseDTO> expenseResponseDTOList = expenses.stream()
                .map(ExpenseResponseDTO::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(DataResponseDTO.success(expenseResponseDTOList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DataResponseDTO<ExpenseResponseDTO>> getExpenseById(
            @PathVariable Long id){

        Expense expense = expenseService.getExpenseById(id);
        ExpenseResponseDTO expenseResponseDTO = ExpenseResponseDTO.from(expense);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(expenseResponseDTO));
    }


    @PostMapping()
    public ResponseEntity<DataResponseDTO<CreatedIdResponseDTO>> addExpense(
            @RequestBody ExpenseRequestDTO expenseRequestDTO){
        Expense expense = expenseRequestDTO.toEntity();
        Long savedId = expenseService.addExpense(expense);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(new CreatedIdResponseDTO(savedId)));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponseDTO<Void>> deleteExpense(@PathVariable long id){
        expenseService.deleteExpense(id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(null));
    }


    @PutMapping("/{id}")
    public ResponseEntity<DataResponseDTO<Expense>> updateExpense(
            @PathVariable long id,
            @RequestBody ExpenseRequestDTO expenseRequestDTO){
        Expense updateExpense = expenseService.updateExpense(id, expenseRequestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(updateExpense));
    }
}

