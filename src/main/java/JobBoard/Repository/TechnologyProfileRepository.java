package JobBoard.Repository;

import JobBoard.model.TechnologyProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyProfileRepository extends JpaRepository<TechnologyProfile, Integer> {
}
