package JobBoard.model;

import JobBoard.Repository.JobOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class JobTechnologyTest {

    @Autowired
    JobOfferRepository jobOfferRepository;

    @Test
    public void givenDuplicateTechnologies_whenUpdate_thenThrowException(){

        Technology technology = new Technology("java");
        JobTechnology jobTechnology = new JobTechnology(technology);

        JobOffer jobOffer = new JobOffer("test job", "test company", "test description", new TechnologyProfile("backend"),
                new ArrayList<>(Arrays.asList(new JobRequirement("requirement"))), new ArrayList<>(Arrays.asList(jobTechnology)));

        jobTechnology.setJobOffer(jobOffer);
        jobOfferRepository.save(jobOffer);

        JobTechnology jobTechnology2 = new JobTechnology(technology);
        jobTechnology2.setJobOffer(jobOffer);
        jobOffer.getTechnologies().add(jobTechnology2);

        assertThrows(DataIntegrityViolationException.class, () ->{
            jobOfferRepository.save(jobOffer);
        });
    }

}