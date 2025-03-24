package capstone.donworry.controller;

import capstone.donworry.domain.Expense;
import capstone.donworry.dto.AddExpenseRequest;
import capstone.donworry.dto.UpdateExpenseRequest;
import capstone.donworry.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CalendarController {

    private final CalendarService calendarService;


    // 항목 추가
    @PostMapping("/api/expense")
    public ResponseEntity<Expense> addExpense(@RequestBody AddExpenseRequest request){
        Expense savedExpense = calendarService.add(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedExpense);
    }

    // 항목 삭제
    @DeleteMapping("/api/expense/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable long id){
        calendarService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    // 항목 수정
    @PutMapping("/api/expense/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable long id,
                                                 @RequestBody UpdateExpenseRequest request){
        Expense updateExpense = calendarService.update(id, request);

        return ResponseEntity.ok()
                .body(updateExpense);
    }
}

