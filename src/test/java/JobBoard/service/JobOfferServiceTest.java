package JobBoard.service;

import JobBoard.Repository.JobOfferRepository;
import JobBoard.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class JobOfferServiceTest {

    @Mock
    JobOfferRepository jobOfferRepository;

    @InjectMocks
    JobOfferService jobOfferService;

    private TechnologyProfile technologyProfile;
    private JobRequirement jobRequirement;
    private JobTechnology jobTechnology;
    private JobOffer jobOffer;

    @BeforeEach
    void setup(){
        technologyProfile = new TechnologyProfile("backend");
        jobRequirement = new JobRequirement("this is a requirement");
        jobTechnology = new JobTechnology(new Technology("test technology"));

        jobOffer = new JobOffer("test job", "test company", "test description", technologyProfile,
                new ArrayList<>(Arrays.asList(jobRequirement)), new ArrayList<>(Arrays.asList(jobTechnology)));
    }

    @Test
    public void givenJobOffer_whenSave_thenReturnJobOffer(){
        given(jobOfferRepository.save(jobOffer)).willReturn(jobOffer);

        JobOffer savedJobOffer = jobOfferService.saveJobOffer(jobOffer);

        assertThat(savedJobOffer).isNotNull();
    }

    @Test
    public void givenJobOfferId_whenGetById_thenReturnJobOffer(){
        jobOffer.setId(1);
        given(jobOfferRepository.getById(1)).willReturn(jobOffer);

        JobOffer savedJobOffer = jobOfferService.getJobOffer(1);

        assertThat(savedJobOffer).isNotNull();
    }

    @Test
    public void givenJobOffer_whenAddRequirement_thenReturnUpdatedList(){
        jobOfferService.addJobRequirement(jobOffer);
        assertThat(jobOffer.getRequirements().size()).isEqualTo(2);
    }

    @Test
    public void givenJobOffer_whenRemoveRequirement_thenReturnUpdatedList(){
        jobOfferService.removeJobRequirement(jobOffer,0);
        assertThat(jobOffer.getRequirements().size()).isEqualTo(0);
    }

    @Test
    public void givenJobOffer_whenAddTechnology_thenReturnUpdatedList(){
        jobOfferService.addJobTechnology(jobOffer);
        assertThat(jobOffer.getTechnologies().size()).isEqualTo(2);
    }

    @Test
    public void givenJobOffer_whenRemoveTechnology_thenReturnUpdatedList(){
        jobOfferService.removeJobTechnology(jobOffer,0);
        assertThat(jobOffer.getTechnologies().size()).isEqualTo(0);
    }

    @Test
    public void givenJobOfferId_whenDelete_thenNothing(){
        jobOffer.setId(1);
        willDoNothing().given(jobOfferRepository).deleteById(1);

        jobOfferService.deleteJobOffer(1);

        verify(jobOfferRepository, times(1)).deleteById(1);
    }

    @Test
    public void givenJobOfferList_whenGetAllJobOffers_thenReturnList(){
        JobOffer jobOffer2 = new JobOffer("test job2", "test company", "test description", technologyProfile,
                new ArrayList<>(Arrays.asList(jobRequirement)), new ArrayList<>(Arrays.asList(jobTechnology)));

        given(jobOfferRepository.findAll()).willReturn(List.of(jobOffer, jobOffer2));

        List<JobOffer> jobOffers = jobOfferService.findAll();

        assertThat(jobOffers).isNotNull();
        assertThat(jobOffers.size()).isEqualTo(2);
    }
}