package JobBoard.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="job_requirement", indexes = @Index(name = "uniqueRequirement", columnList = "requirement, job_offer_id", unique = true))
public class JobRequirement {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "{jobRequirement.validation.requirement}")
    @Column(name="requirement")
    private String requirement;

    @ManyToOne
    private JobOffer jobOffer;

    public JobRequirement() {
    }

    public JobRequirement(String requirement) {
        this.requirement = requirement;
    }

    public JobRequirement(String requirement, JobOffer jobOffer) {
        this.requirement = requirement;
        this.jobOffer = jobOffer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }
}

