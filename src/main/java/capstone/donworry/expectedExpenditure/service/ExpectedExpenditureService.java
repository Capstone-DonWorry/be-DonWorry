package capstone.donworry.expectedExpenditure.service;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expectedExpenditure.repository.ExpectedExpenditureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpectedExpenditureService {

    public final ExpectedExpenditureRepository expectedExpenditureRepository;

    public ExpectedExpenditure getExpectedExpenditure(Long id) {
        return expectedExpenditureRepository.findById(id);
    }
}
