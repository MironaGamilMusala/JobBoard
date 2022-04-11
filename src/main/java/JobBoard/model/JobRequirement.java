package JobBoard.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="job_requirement")
public class JobRequirement {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="requirement")
    private String requirement;

    @ManyToOne
    private JobOffer jobOffer;

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
