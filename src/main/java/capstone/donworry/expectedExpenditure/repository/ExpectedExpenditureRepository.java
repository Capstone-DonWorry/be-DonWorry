package capstone.donworry.expectedExpenditure.repository;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import capstone.donworry.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpectedExpenditureRepository extends JpaRepository<ExpectedExpenditure, Long> {
    Optional<ExpectedExpenditure> findByMemberIdAndExpectedExpenditureId(Long memberId, Long expectedExpenditureId);

    @Query("SELECT e FROM ExpectedExpenditure e WHERE e.member.id = :memberId AND e.date = :date")
    List<ExpectedExpenditure> findByMemberIdAndDateBetween(
            @Param("memberId") Long memberId,
            @Param("date") LocalDate date);
}
