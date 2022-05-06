package JobBoard.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="job_technology")
public class JobTechnology {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Technology can not be blank")
    @Column(name="technology")
    private String technology;

    @ManyToOne
    private JobOffer jobOffer;

    public JobTechnology() {
    }

    public JobTechnology(String technology) {
        this.technology = technology;
    }

    public JobTechnology(String technology, JobOffer jobOffer) {
        this.technology = technology;
        this.jobOffer = jobOffer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }
}

