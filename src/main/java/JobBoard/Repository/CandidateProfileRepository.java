package JobBoard.Repository;

import JobBoard.model.CandidateProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile, Integer> {
    CandidateProfile findByUserId(int id);
}
