package capstone.donworry.calendar.controller;

import capstone.donworry.calendar.dto.DailyExpenseSummaryDto;
import capstone.donworry.calendar.service.CalendarService;
import capstone.donworry.expense.service.ExpenseService;
import capstone.donworry.global.response.DataResponseDTO;
import capstone.donworry.login.common.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/calendar")
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;
    private final ExpenseService expenseService;

    //년/월 보내면 일자별 목표 금액 + 지출합계금액 + 예상지출금액 보내줘야 함
//    @GetMapping
//    public ResponseEntity<DataResponseDTO<MonthlyExpenseSummaryDto>> getMonthlyExpenseSummary(
//            @RequestParam int year,
//            @RequestParam int month,
//            @AuthenticationPrincipal CustomUserDetails userDetails) {
//
//        Long memberId = userDetails.getMember().getId();
//
//        MonthlyExpenseSummaryDto monthlyExpenseSummary =
//                calendarService.getMonthlyExpenseSummary(year, month, memberId);
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(DataResponseDTO.success(monthlyExpenseSummary));
//    }

    @GetMapping
    public ResponseEntity<DataResponseDTO<DailyExpenseSummaryDto>> getDailyExpenseSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMember().getId();
        DailyExpenseSummaryDto dailyExpenseSummary = calendarService.getDailyExpenseSummary(date, memberId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(dailyExpenseSummary));
    }
}
