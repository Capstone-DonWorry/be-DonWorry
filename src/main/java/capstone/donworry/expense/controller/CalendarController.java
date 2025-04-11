package capstone.donworry.expense.controller;

import capstone.donworry.expense.dto.AddExpenseRequest;
import capstone.donworry.expense.dto.UpdateExpenseRequest;
import capstone.donworry.expense.domain.Expense;
import capstone.donworry.expense.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/expense")
public class CalendarController {

    private final CalendarService calendarService;



    @PostMapping()
    public ResponseEntity<Expense> addExpense(@RequestBody AddExpenseRequest request){
        Expense savedExpense = calendarService.add(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedExpense);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable long id){
        calendarService.delete(id);

        return ResponseEntity.ok()
                .build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable long id,
                                                 @RequestBody UpdateExpenseRequest request){
        Expense updateExpense = calendarService.update(id, request);

        return ResponseEntity.ok()
                .body(updateExpense);
    }
}

