package capstone.donworry.expectedExpenditure.service;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expectedExpenditure.dto.ExpectedExpenditureRequestDTO;
import capstone.donworry.expectedExpenditure.repository.ExpectedExpenditureRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpectedExpenditureService {

    public final ExpectedExpenditureRepository expectedExpenditureRepository;

    public ExpectedExpenditure getExpectedExpenditure(Long id) {
        return expectedExpenditureRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터를 찾을 수 없습니다."));
    }

    public Long saveExpectedExpenditure(ExpectedExpenditure expectedExpenditure) {

        ExpectedExpenditure savedExpectedExpenditure = expectedExpenditureRepository.save(expectedExpenditure);

        return savedExpectedExpenditure.getId();
    }

    @Transactional
    public ExpectedExpenditure updateExpectedExpenditure(Long id,
                                                         ExpectedExpenditureRequestDTO expectedExpenditureRequestDTO) {

        ExpectedExpenditure savedExpectedExpenditure = expectedExpenditureRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터를 찾을 수 없습니다."));
        savedExpectedExpenditure.update(expectedExpenditureRequestDTO.getDetails(),
                expectedExpenditureRequestDTO.getAmount(),
                expectedExpenditureRequestDTO.getDate());

        return savedExpectedExpenditure;
    }

    public void deleteExpectedExpenditure(Long id) {
        expectedExpenditureRepository.deleteById(id);
    }
}
