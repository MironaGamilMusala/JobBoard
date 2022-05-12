package JobBoard.service;

import JobBoard.Repository.TechnologyProfileRepository;
import JobBoard.model.TechnologyProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TechnologyProfileServiceTest {

    @Mock
    private TechnologyProfileRepository technologyProfileRepository;

    @InjectMocks
    private TechnologyProfileService technologyProfileService;

    private TechnologyProfile technologyProfile;

    @BeforeEach
    void setup(){
        technologyProfile = new TechnologyProfile("Backend");
    }

    @Test
    public void givenTechnologyList_whenGetAllTechnologies_thenReturnList(){
        TechnologyProfile technologyProfile2 = new TechnologyProfile("Frontend");

        given(technologyProfileRepository.findAll()).willReturn(List.of(technologyProfile, technologyProfile2));

        List<TechnologyProfile> technologies = technologyProfileService.getAllTechnologyProfiles();

        assertThat(technologies).isNotNull();
        assertThat(technologies.size()).isEqualTo(2);
    }

}