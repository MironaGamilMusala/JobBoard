package JobBoard.service;

import JobBoard.Repository.TechnologyRepository;
import JobBoard.model.Technology;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class TechnologyServiceTest {

    @Mock
    private TechnologyRepository technologyRepository;

    @InjectMocks
    private TechnologyService technologyService;

    private Technology technology;

    @BeforeEach
    void setup(){
        technology = new Technology("Java");
    }

    @Test
    public void givenTechnologyList_whenGetAllTechnologies_thenReturnList(){
        Technology technology2 = new Technology("Python");

        given(technologyRepository.findAll()).willReturn(List.of(technology, technology2));

        List<Technology> technologies = technologyService.getAllTechnologies();

        assertThat(technologies).isNotNull();
        assertThat(technologies.size()).isEqualTo(2);
    }
}