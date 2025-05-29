package capstone.donworry.statistics.controller;

import capstone.donworry.global.response.DataResponseDTO;
import capstone.donworry.login.common.CustomUserDetails;
import capstone.donworry.statistics.dto.DailyExpenseDTO;
import capstone.donworry.statistics.dto.PaymentExpenseDTO;
import capstone.donworry.statistics.service.StatisticsService;
import capstone.donworry.statistics.dto.WeeklyExpenseDTO;
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
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/weekly")
    public ResponseEntity<DataResponseDTO<List<WeeklyExpenseDTO>>> getWeeklyExpenseByMonth(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam int year,
            @RequestParam int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());


        if (userDetails == null) {
            // 인증 실패 상태임
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long memberId = userDetails.getMember().getId();
        System.out.println("memberId = " + memberId);

        List<WeeklyExpenseDTO> result = statisticsService.getWeeklyExpense(memberId, startDate, endDate);

        return ResponseEntity.ok(DataResponseDTO.success(result));
    }

    @GetMapping("/daily")
    public ResponseEntity<DataResponseDTO<List<DailyExpenseDTO>>> getDailyExpenseByWeek(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Long memberId = userDetails.getMember().getId();
        List<DailyExpenseDTO> dailyStats = statisticsService.getDailyExpense(memberId, startDate, endDate);

        return ResponseEntity.ok(DataResponseDTO.success(dailyStats));
    }


    @GetMapping("/weekly/payment-method")
    public ResponseEntity<DataResponseDTO<List<PaymentExpenseDTO>>> getWeeklyPaymentStats(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Long memberId = userDetails.getMember().getId();
        List<PaymentExpenseDTO> paymentStats = statisticsService.getWeeklyPaymentExpense(memberId, startDate, endDate);
        return ResponseEntity.ok(DataResponseDTO.success(paymentStats));
    }
}

