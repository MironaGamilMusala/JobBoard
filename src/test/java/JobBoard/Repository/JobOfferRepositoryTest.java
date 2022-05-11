package JobBoard.Repository;

import JobBoard.model.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class JobOfferRepositoryTest {
    
    @Autowired
    JobOfferRepository jobOfferRepository;

    private TechnologyProfile technologyProfile;
    private JobRequirement jobRequirement;
    private JobTechnology jobTechnology;
    private JobOffer jobOffer;
    private
    
    @BeforeEach
    void setup(){
        technologyProfile = new TechnologyProfile("backend");
        jobRequirement = new JobRequirement("this is a requirement");
        jobTechnology = new JobTechnology(new Technology("test technology"));

        jobOffer = new JobOffer("test job", "test company", "test description", technologyProfile,
                new ArrayList<>(Arrays.asList(jobRequirement)), new ArrayList<>(Arrays.asList(jobTechnology)));
    }

    @Test
    public void givenCandidateObject_whenSave_thenReturnSavedJobOffer(){
        JobOffer savedJobOffer = jobOfferRepository.save(jobOffer);
        assertThat(savedJobOffer).isNotNull();
        assertThat(savedJobOffer.getId()).isGreaterThan(0);
    }

    @Test
    public void givenJobOffers_whenFindAll_thenGetJobOffersList(){
        JobOffer jobOffer2 = new JobOffer("test job", "test company", "test description", technologyProfile,
                new ArrayList<>(Arrays.asList(jobRequirement)), new ArrayList<>(Arrays.asList(jobTechnology)));

        jobOfferRepository.save(jobOffer);
        jobOfferRepository.save(jobOffer2);

        List<JobOffer> jobOfferList = jobOfferRepository.findAll();

        assertThat(jobOfferList.size()).isEqualTo(2);
    }

    @Test
    public void givenJobOffer_whenGetById_thenGetJobOffer(){
        jobOfferRepository.save(jobOffer);

        JobOffer jobOfferById = jobOfferRepository.getById(jobOffer.getId());

        assertThat(jobOfferById).isNotNull();
        assertThat(jobOfferById.getTitle()).isEqualTo("test job");
    }

    @Test
    public void givenJobOffer_whenUpdate_thenGetUpdatedInfo(){
        jobOfferRepository.save(jobOffer);

        jobOffer.setTitle("updated title");
        jobOfferRepository.save(jobOffer);
        JobOffer updatedJobOffer = jobOfferRepository.getById(jobOffer.getId());

        assertThat(updatedJobOffer).isNotNull();
        assertThat(updatedJobOffer.getTitle()).isEqualTo("updated title");
    }

    @Test
    public void givenJobOffer_whenDelete_thenRemoved(){
        jobOfferRepository.save(jobOffer);

        jobOfferRepository.deleteById(jobOffer.getId());
        Optional<JobOffer> savedJobOffer = jobOfferRepository.findById(jobOffer.getId());

        assertThat(savedJobOffer).isEmpty();
    }


    @Test
    public void given_when_then(){

    }


}