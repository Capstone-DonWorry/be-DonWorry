package capstone.donworry.expectedExpenditure.controller;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.utils.dto.DataResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;
import java.util.Date;

@RestController
@RequestMapping("/api/expectedExpenditure")
public class ExpectedExpenditureController {

    @GetMapping("/{id}")
    public ResponseEntity<DataResponseDTO<ExpectedExpenditure>> getExpectedExpenditure(
            @PathVariable Long id) {


    }
}
