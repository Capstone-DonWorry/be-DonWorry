package capstone.donworry.expectedExpenditure.service;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.expectedExpenditure.dto.ExpectedExpenditureRequestDTO;
import capstone.donworry.expectedExpenditure.repository.ExpectedExpenditureRepository;
import capstone.donworry.member.domain.Member;
import capstone.donworry.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ExpectedExpenditureService {

    private  final ExpectedExpenditureRepository expectedExpenditureRepository;
    private final MemberRepository memberRepository;

    public ExpectedExpenditure getExpectedExpenditure(Long memberId, Long expectedExpenditureId) {
        return expectedExpenditureRepository.findByMemberIdAndExpectedExpenditureId(memberId, expectedExpenditureId).
                orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터를 찾을 수 없습니다."));
    }

    public Long saveExpectedExpenditure(Long memberId, ExpectedExpenditureRequestDTO expectedExpenditureRequestDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));

        ExpectedExpenditure expectedExpenditure = expectedExpenditureRequestDTO.toEntity(member);
        ExpectedExpenditure savedExpectedExpenditure = expectedExpenditureRepository.save(expectedExpenditure);

        return savedExpectedExpenditure.getExpectedExpenditureId();
    }

    public ExpectedExpenditure updateExpectedExpenditure(Long memberId,
                                                         Long expectedExpenditureId,
                                                         ExpectedExpenditureRequestDTO expectedExpenditureRequestDTO) {

        ExpectedExpenditure expectedExpenditure = expectedExpenditureRepository
                .findByMemberIdAndExpectedExpenditureId(memberId, expectedExpenditureId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터를 찾을 수 없습니다."));

        expectedExpenditure.update(expectedExpenditureRequestDTO.getDetails(),
                expectedExpenditureRequestDTO.getAmount(),
                expectedExpenditure.getDate());

        return expectedExpenditure;
    }

    public void deleteExpectedExpenditure(Long memberId, Long expectedExpenditureId) {
        ExpectedExpenditure expectedExpenditure = expectedExpenditureRepository
                .findByMemberIdAndExpectedExpenditureId(memberId, expectedExpenditureId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 데이터를 찾을 수 없습니다."));

        log.info("예상 지출 삭제 완료 : {}", expectedExpenditure.getExpectedExpenditureId());
        expectedExpenditureRepository.delete(expectedExpenditure);
    }
}
