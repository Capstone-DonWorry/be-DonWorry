package capstone.donworry.statistics.controller;

import capstone.donworry.expense.domain.ExpenseCategory;
import capstone.donworry.global.response.DataResponseDTO;
import capstone.donworry.login.common.CustomUserDetails;
import capstone.donworry.statistics.dto.*;
import capstone.donworry.statistics.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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


        Long memberId = userDetails.getMember().getId();
        List<WeeklyExpenseDTO> result = statisticsService.getWeeklyExpense(memberId, startDate, endDate);

        return ResponseEntity.ok(DataResponseDTO.success(result));
    }

    @GetMapping("/weekly/detail")
    public ResponseEntity<DataResponseDTO<WeeklyDetailExpenseDTO>> getWeeklyDetailStatistics(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        Long memberId = userDetails.getMember().getId();
        WeeklyDetailExpenseDTO dto = statisticsService.getWeeklyDetailStatistics(memberId, startDate, endDate);

        return ResponseEntity.ok(DataResponseDTO.success(dto));
    }


    @GetMapping("/monthly/category")
    public ResponseEntity<DataResponseDTO<MonthlyStatisticsDTO>> getMonthlyCategoryStatistics(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam int year,
            @RequestParam int month) {

        Long memberId = userDetails.getMember().getId();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        MonthlyStatisticsDTO dto = statisticsService.getMonthlyCategoryStatistics(memberId, startDate, endDate);

        return ResponseEntity.ok(DataResponseDTO.success(dto));
    }

    @GetMapping("/monthly/category/detail")
    public ResponseEntity<DataResponseDTO<CategoryExpenseDetailDTO>> getCategoryDetail(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam int year,
            @RequestParam int month,
            @RequestParam String category) {

        Long memberId = userDetails.getMember().getId();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

        ExpenseCategory categoryEnum;
        try {
            categoryEnum = ExpenseCategory.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "존재하지 않는 카테고리입니다.");
        }

        CategoryExpenseDetailDTO dto = statisticsService.getCategoryExpenseDetail(memberId, categoryEnum, startDate, endDate);
        return ResponseEntity.ok(DataResponseDTO.success(dto));
    }



}

