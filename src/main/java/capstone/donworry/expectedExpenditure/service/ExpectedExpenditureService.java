package capstone.donworry.expectedExpenditure.service;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expectedExpenditure.dto.ExpectedExpenditureRequestDTO;
import capstone.donworry.expectedExpenditure.repository.ExpectedExpenditureRepository;
import capstone.donworry.member.domain.Member;
import capstone.donworry.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ExpectedExpenditureService {

    private  final ExpectedExpenditureRepository expectedExpenditureRepository;
    private final MemberRepository memberRepository;

    public ExpectedExpenditure getExpectedExpenditure(Long id) {
        return expectedExpenditureRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터를 찾을 수 없습니다."));
    }

    public Long saveExpectedExpenditure(Long memberId, ExpectedExpenditureRequestDTO expectedExpenditureRequestDTO) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
        ExpectedExpenditure expectedExpenditure = expectedExpenditureRequestDTO.toEntity(member);
        ExpectedExpenditure savedExpectedExpenditure = expectedExpenditureRepository.save(expectedExpenditure);

        return savedExpectedExpenditure.getId();
    }

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
