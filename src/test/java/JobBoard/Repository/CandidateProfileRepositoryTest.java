package JobBoard.Repository;

import JobBoard.model.CandidateProfile;
import JobBoard.model.CustomUser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
class CandidateProfileRepositoryTest {

    @Autowired
    CandidateProfileRepository candidateProfileRepository;

    private CustomUser customUser;
    private CandidateProfile candidateProfile;

    @BeforeEach
    void setup(){
        customUser = new CustomUser("user","123");
        candidateProfile = new CandidateProfile(customUser);
    }

    @Test
    public void givenCandidateObject_whenSave_thenReturnSavedCandidate(){
        CandidateProfile savedCandidateProfile = candidateProfileRepository.save(candidateProfile);
        assertThat(savedCandidateProfile).isNotNull();
        assertThat(savedCandidateProfile.getId()).isGreaterThan(0);
    }

    @Test
    public void givenCandidateProfiles_whenFindAll_thenGetCandidateProfilesList(){
        CustomUser customUser2 = new CustomUser("user2", "123");
        CandidateProfile candidateProfile2 = new CandidateProfile(customUser2);

        candidateProfileRepository.save(candidateProfile);
        candidateProfileRepository.save(candidateProfile2);

        List<CandidateProfile> candidateProfileList = candidateProfileRepository.findAll();

        assertThat(candidateProfileList.size()).isEqualTo(2);
    }

    @Test
    public void givenCandidateProfile_whenGetById_thenGetCandidateProfile(){
        candidateProfileRepository.save(candidateProfile);

        CandidateProfile candidateProfileById = candidateProfileRepository.getById(candidateProfile.getId());

        assertThat(candidateProfileById).isNotNull();
        assertThat(candidateProfileById.getUser().getUsername()).isEqualTo("user");
    }

    @Test
    public void givenCandidateProfile_whenFindByUserId_thenGetCandidateProfile(){

        candidateProfileRepository.save(candidateProfile);

        CandidateProfile candidateProfileByUserId = candidateProfileRepository.findByUserId(candidateProfile.getUser().getId());

        assertThat(candidateProfileByUserId).isNotNull();
        assertThat(candidateProfileByUserId.getUser().getUsername()).isEqualTo("user");

    }

    @Test
    public void givenCandidateProfile_whenUpdate_thenGetUpdatedInfo(){
        candidateProfileRepository.save(candidateProfile);
        String previousExperience = "this is previous experience";
        candidateProfile.setPreviousExperience(previousExperience);

        candidateProfileRepository.save(candidateProfile);
        CandidateProfile updatedCandidateProfile = candidateProfileRepository.getById(candidateProfile.getId());

        assertThat(updatedCandidateProfile).isNotNull();
        assertThat(updatedCandidateProfile.getPreviousExperience()).isEqualTo(previousExperience);
    }

    @Test
    public void givenCandidateProfile_whenDelete_thenRemoved(){
        candidateProfileRepository.save(candidateProfile);

        candidateProfileRepository.deleteById(candidateProfile.getId());
        Optional<CandidateProfile> savedCandidateProfile = candidateProfileRepository.findById(candidateProfile.getId());

        assertThat(savedCandidateProfile).isEmpty();
    }
}