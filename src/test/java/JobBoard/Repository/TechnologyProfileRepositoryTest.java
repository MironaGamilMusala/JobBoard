package JobBoard.Repository;

import JobBoard.model.TechnologyProfile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TechnologyProfileRepositoryTest {

    @Autowired
    TechnologyProfileRepository technologyProfileRepository;

    private TechnologyProfile technologyProfile;

    @BeforeEach
    void setup(){
        technologyProfile = new TechnologyProfile("backend");
    }

    @Test
    public void givenCandidateObject_whenSave_thenReturnSavedTechnologyProfile(){
        TechnologyProfile savedTechnologyProfile = technologyProfileRepository.save(technologyProfile);
        assertThat(savedTechnologyProfile).isNotNull();
        assertThat(savedTechnologyProfile.getId()).isGreaterThan(0);
    }

    @Test
    public void givenTechnologyProfiles_whenFindAll_thenGetTechnologyProfilesList(){
        TechnologyProfile technologyProfile2 = new TechnologyProfile("fullstack");

        technologyProfileRepository.save(technologyProfile);
        technologyProfileRepository.save(technologyProfile2);

        List<TechnologyProfile> technologyProfileList = technologyProfileRepository.findAll();

        assertThat(technologyProfileList.size()).isEqualTo(2);
    }

    @Test
    public void givenTechnologyProfile_whenGetById_thenGetTechnologyProfile(){
        technologyProfileRepository.save(technologyProfile);

        TechnologyProfile technologyProfileById = technologyProfileRepository.getById(technologyProfile.getId());

        assertThat(technologyProfileById).isNotNull();
        assertThat(technologyProfileById.getName()).isEqualTo("backend");
    }

    @Test
    public void givenTechnologyProfile_whenUpdate_thenGetUpdatedInfo(){
        technologyProfileRepository.save(technologyProfile);

        technologyProfile.setName("frontend");
        technologyProfileRepository.save(technologyProfile);
        TechnologyProfile updatedTechnologyProfile = technologyProfileRepository.getById(technologyProfile.getId());

        assertThat(updatedTechnologyProfile).isNotNull();
        assertThat(updatedTechnologyProfile.getName()).isEqualTo("frontend");
    }

    @Test
    public void givenTechnologyProfile_whenDelete_thenRemoved(){
        technologyProfileRepository.save(technologyProfile);

        technologyProfileRepository.deleteById(technologyProfile.getId());
        Optional<TechnologyProfile> savedTechnologyProfile = technologyProfileRepository.findById(technologyProfile.getId());

        assertThat(savedTechnologyProfile).isEmpty();
    }


}