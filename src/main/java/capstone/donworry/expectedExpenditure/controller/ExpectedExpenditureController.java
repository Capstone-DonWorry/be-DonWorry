package capstone.donworry.expectedExpenditure.controller;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expectedExpenditure.service.ExpectedExpenditureService;
import capstone.donworry.global.response.DataResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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


}
