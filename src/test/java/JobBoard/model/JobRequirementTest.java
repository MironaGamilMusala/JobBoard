package JobBoard.model;

import JobBoard.Repository.JobOfferRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class
JobRequirementTest {

    private Validator validator;

    private JobRequirement jobRequirement;

    @Autowired
    JobOfferRepository jobOfferRepository;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        jobRequirement = new JobRequirement("test requirements");
    }

    @Test
    public void givenValidRequirement_whenValidate_thenGetNoErrors() {
        Set<ConstraintViolation<JobRequirement>> violations = validator.validate(jobRequirement);
        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void givenNullRequirement_whenValidate_thenGetError() {
        jobRequirement.setRequirement(null);
        Set<ConstraintViolation<JobRequirement>> violations = validator.validate(jobRequirement);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenEmptyRequirement_whenValidate_thenGetError() {
        jobRequirement.setRequirement("");
        Set<ConstraintViolation<JobRequirement>> violations = validator.validate(jobRequirement);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenBlankRequirement_whenValidate_thenGetError() {
        jobRequirement.setRequirement("  ");
        Set<ConstraintViolation<JobRequirement>> violations = validator.validate(jobRequirement);
        assertThat(violations.size()).isEqualTo(1);
    }

    @Test
    public void givenDuplicateRequirements_whenUpdate_thenThrowException(){
        JobOffer jobOffer = new JobOffer("test job", "test company", "test description", new TechnologyProfile("backend"),
                new ArrayList<>(Arrays.asList(jobRequirement)), new ArrayList<>(Arrays.asList(new JobTechnology(new Technology("java")))));

        jobRequirement.setJobOffer(jobOffer);
        jobOfferRepository.save(jobOffer);

        JobRequirement jobRequirement2 = new JobRequirement("test requirements");
        jobRequirement2.setJobOffer(jobOffer);
        jobOffer.getRequirements().add(jobRequirement2);

        assertThrows(DataIntegrityViolationException.class, () ->{
            jobOfferRepository.save(jobOffer);
        });
    }

}