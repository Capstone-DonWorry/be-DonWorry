package capstone.donworry.member.controller;

import capstone.donworry.global.response.DataResponseDTO;
import capstone.donworry.login.common.CustomUserDetails;
import capstone.donworry.member.domain.Member;
import capstone.donworry.member.dto.MemberJoinRequestDTO;
import capstone.donworry.member.dto.MemberResponseDTO;
import capstone.donworry.member.dto.MemberUpdateRequestDTO;
import capstone.donworry.member.dto.PasswordChangeRequestDTO;
import capstone.donworry.member.service.MemberService;
import capstone.donworry.monthlyExpenseGoals.domain.MonthlyExpenseGoal;
import capstone.donworry.monthlyExpenseGoals.service.MonthlyExpenseGoalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;
    private final MonthlyExpenseGoalService monthlyExpenseGoalService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(
            @RequestBody @Valid MemberJoinRequestDTO memberJoinRequestDTO) {

        memberService.join(memberJoinRequestDTO);
        return ResponseEntity.ok(DataResponseDTO.successWithMessage("회원가입 완료", null));

    }

    @DeleteMapping
    public ResponseEntity<DataResponseDTO<Void>> deleteAccount(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMember().getId();
        memberService.deleteMember(memberId);

        return ResponseEntity.ok(DataResponseDTO.success(null));
    }

    @GetMapping("/info")
    public ResponseEntity<DataResponseDTO<MemberResponseDTO>> getUserInfo(
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Member member = memberService.getLoginMemberByLoginId(userDetails.getUsername());
        LocalDate today = LocalDate.now();
        MonthlyExpenseGoal monthlyExpenseGoal = monthlyExpenseGoalService
                .getMonthGoal(member.getId(), today.getYear(), today.getMonthValue(), today);

        MemberResponseDTO responseDTO = MemberResponseDTO.from(member, monthlyExpenseGoal.getGoalAmount());

        return ResponseEntity.ok(DataResponseDTO.success(responseDTO));
    }

    @PutMapping("/info")
    public ResponseEntity<DataResponseDTO<MemberResponseDTO>> updateUserInfo(
            @RequestBody @Valid MemberUpdateRequestDTO requestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMember().getId();
        Member updatedMember = memberService.updateMember(memberId, requestDTO);

        LocalDate today = LocalDate.now();
        MonthlyExpenseGoal monthlyExpenseGoal = monthlyExpenseGoalService
                .getMonthGoal(memberId, today.getYear(), today.getMonthValue(), today);


        MemberResponseDTO responseDTO = MemberResponseDTO.from(updatedMember, monthlyExpenseGoal.getGoalAmount());
        return ResponseEntity.ok(DataResponseDTO.successWithMessage("정보 수정 완료", responseDTO));
    }

    @PutMapping("/password")
    public ResponseEntity<DataResponseDTO<Void>> changePassword(
            @RequestBody @Valid PasswordChangeRequestDTO requestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMember().getId();
        memberService.changePassword(memberId, requestDTO);

        return ResponseEntity.ok(DataResponseDTO.success(null));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> adminPage() {
        return ResponseEntity.ok("관리자 전용 페이지입니다.");
    }
}

