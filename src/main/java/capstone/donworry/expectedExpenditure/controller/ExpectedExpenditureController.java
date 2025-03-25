package capstone.donworry.expectedExpenditure.controller;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.utils.dto.DataResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/expectedExpenditure")
public class ExpectedExpenditureController {

    @GetMapping("/{id}")
    public ResponseEntity<DataResponseDTO<ExpectedExpenditure>>
}
