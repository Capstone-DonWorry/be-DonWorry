package capstone.donworry.expectedExpenditure.service;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expectedExpenditure.repository.ExpectedExpenditureRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpectedExpenditureService {

    public final ExpectedExpenditureRepository expectedExpenditureRepository;

    public ExpectedExpenditure getExpectedExpenditure(Long id) {
        return expectedExpenditureRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터를 찾을 수 없습니다."));
    }
}
