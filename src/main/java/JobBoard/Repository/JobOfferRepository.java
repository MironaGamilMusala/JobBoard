package JobBoard.Repository;

import JobBoard.model.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobOfferRepository extends JpaRepository<JobOffer, Integer> {
    void deleteById(int id);
}
