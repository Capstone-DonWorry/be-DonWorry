package capstone.donworry.expectedExpenditure.controller;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expectedExpenditure.dto.CreatedIdResponseDTO;
import capstone.donworry.expectedExpenditure.dto.ExpectedExpenditureRequestDTO;
import capstone.donworry.expectedExpenditure.service.ExpectedExpenditureService;
import capstone.donworry.global.response.DataResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/expectedExpenditure")
@RequiredArgsConstructor
public class ExpectedExpenditureController {

    public final ExpectedExpenditureService expectedExpenditureService;

    @GetMapping("/{id}")
    public ResponseEntity<DataResponseDTO<ExpectedExpenditure>> getExpectedExpenditure(
            @PathVariable Long id) {
        ExpectedExpenditure expectedExpenditure = expectedExpenditureService.getExpectedExpenditure(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(expectedExpenditure));
    }

    @PostMapping
    public ResponseEntity<DataResponseDTO<CreatedIdResponseDTO>> createExpectedExpenditure(
            @RequestBody ExpectedExpenditureRequestDTO expectedExpenditureRequestDTO) {

        ExpectedExpenditure expectedExpenditure = expectedExpenditureRequestDTO.toEntity();
        Long savedId = expectedExpenditureService.saveExpectedExpenditure(expectedExpenditure);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(new CreatedIdResponseDTO(savedId)));
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<DataResponseDTO<ExpectedExpenditure>> editExpectedExpenditure(
            @PathVariable Long id,
            @RequestBody ExpectedExpenditureRequestDTO expectedExpenditureRequestDTO) {

        ExpectedExpenditure updatedExpectedExpenditure = expectedExpenditureService.
                updateExpectedExpenditure(id, expectedExpenditureRequestDTO);

        return ResponseEntity.status(HttpStatus.OK)
                .body(DataResponseDTO.success(updatedExpectedExpenditure));
    }
}
