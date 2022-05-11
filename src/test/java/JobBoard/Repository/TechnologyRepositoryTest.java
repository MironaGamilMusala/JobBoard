package JobBoard.Repository;

import JobBoard.model.Technology;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TechnologyRepositoryTest {
    
    @Autowired
    TechnologyRepository technologyRepository;
    
    private Technology technology;

    @BeforeEach
    void setup(){
        technology = new Technology("java");
    }

    @Test
    public void givenCandidateObject_whenSave_thenReturnSavedTechnology(){
        Technology savedTechnology = technologyRepository.save(technology);
        assertThat(savedTechnology).isNotNull();
        assertThat(savedTechnology.getId()).isGreaterThan(0);
    }

    @Test
    public void givenTechnologies_whenFindAll_thenGetTechnologiesList(){
        Technology technology2 = new Technology("python");

        technologyRepository.save(technology);
        technologyRepository.save(technology2);

        List<Technology> technologyList = technologyRepository.findAll();

        assertThat(technologyList.size()).isEqualTo(2);
    }

    @Test
    public void givenTechnology_whenGetById_thenGetTechnology(){
        technologyRepository.save(technology);

        Technology technologyById = technologyRepository.getById(technology.getId());

        assertThat(technologyById).isNotNull();
        assertThat(technologyById.getName()).isEqualTo("java");
    }

    @Test
    public void givenTechnology_whenUpdate_thenGetUpdatedInfo(){
        technologyRepository.save(technology);

        technology.setName("spring");
        technologyRepository.save(technology);
        Technology updatedTechnology = technologyRepository.getById(technology.getId());

        assertThat(updatedTechnology).isNotNull();
        assertThat(updatedTechnology.getName()).isEqualTo("spring");
    }

    @Test
    public void givenTechnology_whenDelete_thenRemoved(){
        technologyRepository.save(technology);

        technologyRepository.deleteById(technology.getId());
        Optional<Technology> savedTechnology = technologyRepository.findById(technology.getId());

        assertThat(savedTechnology).isEmpty();
    }

}