package capstone.donworry.expectedExpenditure.controller;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expectedExpenditure.dto.CreatedIdResponseDTO;
import capstone.donworry.expectedExpenditure.dto.ExpectedExpenditureRequestDTO;
import capstone.donworry.expectedExpenditure.dto.ExpectedExpenditureResponseDTO;
import capstone.donworry.expectedExpenditure.service.ExpectedExpenditureService;
import capstone.donworry.global.response.DataResponseDTO;
import capstone.donworry.login.common.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expectedExpenditure")
@RequiredArgsConstructor
@Slf4j
public class ExpectedExpenditureController {

    public final ExpectedExpenditureService expectedExpenditureService;

    @GetMapping("/{id}")
    public ResponseEntity<DataResponseDTO<ExpectedExpenditureResponseDTO>> getExpectedExpenditure(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMember().getId();
        ExpectedExpenditure expectedExpenditure = expectedExpenditureService.getExpectedExpenditure(id, memberId);
        ExpectedExpenditureResponseDTO expectedExpenditureResponseDTO = ExpectedExpenditureResponseDTO.from(expectedExpenditure);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(expectedExpenditureResponseDTO));
    }

    @PostMapping
    public ResponseEntity<DataResponseDTO<CreatedIdResponseDTO>> createExpectedExpenditure(
            @RequestBody ExpectedExpenditureRequestDTO expectedExpenditureRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMember().getId();
        Long savedId = expectedExpenditureService.saveExpectedExpenditure(memberId, expectedExpenditureRequestDTO);

        log.info("expectedExpenditure.getDetails = {}", expectedExpenditureRequestDTO.getDetails());
        log.info("expectedExpenditure.getAmount = {}", expectedExpenditureRequestDTO.getAmount());
        log.info("expectedExpenditure.getDate = {}", expectedExpenditureRequestDTO.getDate());

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(new CreatedIdResponseDTO(savedId)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponseDTO<ExpectedExpenditureResponseDTO>> editExpectedExpenditure(
            @PathVariable Long id,
            @RequestBody ExpectedExpenditureRequestDTO expectedExpenditureRequestDTO,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMember().getId();
        ExpectedExpenditure updatedExpectedExpenditure = expectedExpenditureService.
                updateExpectedExpenditure(memberId, id, expectedExpenditureRequestDTO);
        ExpectedExpenditureResponseDTO expectedExpenditureResponseDTO = ExpectedExpenditureResponseDTO.from(updatedExpectedExpenditure);

        log.info("expectedExpenditure.getDetails = {}", expectedExpenditureRequestDTO.getDetails());
        log.info("expectedExpenditure.getAmount = {}", expectedExpenditureRequestDTO.getAmount());
        log.info("expectedExpenditure.getDate = {}", expectedExpenditureRequestDTO.getDate());


        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(expectedExpenditureResponseDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponseDTO<Void>> deleteExpectedExpenditure(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        Long memberId = userDetails.getMember().getId();
        expectedExpenditureService.deleteExpectedExpenditure(memberId, id);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(null));
    }
}
