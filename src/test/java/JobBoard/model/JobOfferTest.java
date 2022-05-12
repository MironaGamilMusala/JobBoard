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
class JobOfferTest {

    private Validator validator;
    private JobOffer jobOffer;
    private JobRequirement jobRequirement;
    private JobTechnology jobTechnology;

    @Autowired
    JobOfferRepository jobOfferRepository;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        jobRequirement = new JobRequirement("test requirements");
        jobTechnology = new JobTechnology(new Technology("java"));

        jobOffer = new JobOffer("test job", "test company name", "test description",
                new TechnologyProfile("backend"), new ArrayList<>(Arrays.asList(jobRequirement)),
                new ArrayList<>(Arrays.asList(jobTechnology)));
    }

    @Test
    public void givenValidJobOffer_whenValidate_thenGetNoErrors() {
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void givenNullTitle_whenValidate_thenGetError() {
        jobOffer.setTitle(null);
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenEmptyTitle_whenValidate_thenGetError() {
        jobOffer.setTitle("");
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenBlankTitle_whenValidate_thenGetError() {
        jobOffer.setTitle("  ");
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenNullCompanyName_whenValidate_thenGetError() {
        jobOffer.setCompanyName(null);
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenEmptyCompanyName_whenValidate_thenGetError() {
        jobOffer.setCompanyName("");
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenBlankCompanyName_whenValidate_thenGetError() {
        jobOffer.setCompanyName("  ");
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenNullDescription_whenValidate_thenGetError() {
        jobOffer.setDescription(null);
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenEmptyDescription_whenValidate_thenGetError() {
        jobOffer.setDescription("");
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenBlankDescription_whenValidate_thenGetError() {
        jobOffer.setDescription("  ");
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenNullTechnologyProfile_whenValidate_thenGetError() {
        jobOffer.setTechnologyProfile(null);
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenNullRequirements_whenValidate_thenGetError() {
        jobOffer.setRequirements(null);
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenEmptyRequirements_whenValidate_thenGetError() {
        jobOffer.setRequirements(new ArrayList<>());
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenNullTechnologies_whenValidate_thenGetError() {
        jobOffer.setTechnologies(null);
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenEmptyTechnologies_whenValidate_thenGetError() {
        jobOffer.setTechnologies(new ArrayList<>());
        Set<ConstraintViolation<JobOffer>> violations = validator.validate(jobOffer);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenJobOffer_whenSave_thenGetLists() {

        JobOffer savedJobOffer = jobOfferRepository.save(jobOffer);
        assertThat(savedJobOffer.getTitle()).isEqualTo("test job");
        assertThat(savedJobOffer.getRequirements().size()).isEqualTo(1);
        assertThat(savedJobOffer.getTechnologies().size()).isEqualTo(1);

    }

    @Test
    public void givenJobOffer_whenUpdateRequirements_thenGetUpdatedList() {

        JobOffer savedJobOffer = jobOfferRepository.save(jobOffer);

        savedJobOffer.getRequirements().add(new JobRequirement("test requirement 2"));

        JobOffer updatedData = jobOfferRepository.save(jobOffer);
        assertThat(savedJobOffer.getRequirements().size()).isEqualTo(2);
        assertThat(savedJobOffer.getTechnologies().size()).isEqualTo(1);

    }

    @Test
    public void givenDuplicateJobOffer_whenSave_thenThrowException(){

        jobOfferRepository.save(jobOffer);

        JobOffer jobOffer2 = new JobOffer("test job", "test company name", "test description",
                new TechnologyProfile("backend"), new ArrayList<>(Arrays.asList(jobRequirement)),
                new ArrayList<>(Arrays.asList(jobTechnology)));

        assertThrows(DataIntegrityViolationException.class, () -> {
            jobOfferRepository.save(jobOffer2);
        });
    }
}