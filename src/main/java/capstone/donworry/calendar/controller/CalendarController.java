package capstone.donworry.calendar.controller;

import capstone.donworry.global.response.DataResponseDTO;
import capstone.donworry.login.common.CustomUserDetails;
import jakarta.websocket.server.PathParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calendar")
public class CalendarController {

    @GetMapping
    public ResponseEntity<DataResponseDTO<?>> getAllExpenseAndExpectedExpenditure(
            @RequestParam int year,
            @RequestParam int month,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMember().getId();

        return null;
    }
}
