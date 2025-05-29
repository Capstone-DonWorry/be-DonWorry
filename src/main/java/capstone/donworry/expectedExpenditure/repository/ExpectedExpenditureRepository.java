package capstone.donworry.expectedExpenditure.repository;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpectedExpenditureRepository extends JpaRepository<ExpectedExpenditure, Long> {
    Optional<ExpectedExpenditure> findByMemberIdAndExpectedExpenditureId(Long memberId, Long expectedExpenditureId);
}
