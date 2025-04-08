package capstone.donworry.expectedExpenditure.repository;

import capstone.donworry.expectedExpenditure.domain.ExpectedExpenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpectedExpenditureRepository extends JpaRepository<ExpectedExpenditure, Long> {
}
