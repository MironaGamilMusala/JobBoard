package JobBoard.Repository;

import JobBoard.model.CandidateTechnology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateTechnologyRepository extends JpaRepository<CandidateTechnology, Integer> {
}
