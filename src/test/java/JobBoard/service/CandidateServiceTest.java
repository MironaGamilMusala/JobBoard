package JobBoard.service;

import JobBoard.Repository.CandidateProfileRepository;
import JobBoard.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CandidateServiceTest {

    @Mock
    CandidateProfileRepository candidateProfileRepository;

    @Mock
    JobOfferService jobOfferService;

    @InjectMocks
    CandidateService candidateService;

    private CustomUser customUser;

    private CandidateProfile candidateProfile;

    @BeforeEach
    void setup(){
        customUser = new CustomUser("user", "123");
        candidateProfile = new CandidateProfile(customUser);
    }

    @Test
    public void givenCandidateId_whenGetById_thenReturnCandidateProfile(){
        candidateProfile.setId(1);
        given(candidateProfileRepository.getById(1)).willReturn(candidateProfile);

        CandidateProfile savedCandidateProfile = candidateService.getCandidate(1);

        assertThat(savedCandidateProfile).isNotNull();
    }

    @Test
    public void givenUserId_whenGetByUserId_thenReturnCandidateProfile(){
        customUser.setId(1);
        given(candidateProfileRepository.findByUserId(1)).willReturn(candidateProfile);

        CandidateProfile savedCandidateProfile = candidateService.getCandidateByUserId(1);

        assertThat(savedCandidateProfile).isNotNull();
    }

    @Test
    public void givenCandidateProfile_whenAddTechnology_thenReturnUpdatedList(){
        candidateService.addCandidateTechnology(candidateProfile);
        assertThat(candidateProfile.getTechnologies().size()).isEqualTo(1);
    }

    @Test
    public void givenCandidateProfile_whenRemoveTechnology_thenReturnUpdatedList(){
        candidateService.addCandidateTechnology(candidateProfile);
        candidateService.removeCandidateTechnology(candidateProfile,0);
        assertThat(candidateProfile.getTechnologies().size()).isEqualTo(0);
    }

    @Test
    public void givenCandidate_whenApplyToJob_thenReturnUpdatedAppliedJobsList(){
        JobOffer jobOffer = new JobOffer("test job", "test company", "test description", new TechnologyProfile("backend"),
                new ArrayList<>(Arrays.asList(new JobRequirement("requirement"))), new ArrayList<>(Arrays.asList(new JobTechnology(new Technology("java")))));

        jobOfferService.saveJobOffer(jobOffer);
        candidateProfile.setId(1);

        given(candidateProfileRepository.getById(1)).willReturn(candidateProfile);
        given(candidateProfileRepository.save(candidateProfile)).willReturn(candidateProfile);

        candidateService.addAppliedJob(jobOffer.getId(), candidateProfile.getId());

        assertThat(candidateProfile.getAppliedJobs().size()).isEqualTo(1);
    }

}