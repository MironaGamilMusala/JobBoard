package JobBoard.model;

import javax.persistence.*;

@Entity
@Table(name="job_technology", indexes = @Index(name = "uniqueTechnology", columnList = "technology_id, job_offer_id", unique = true))
public class JobTechnology {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="technology_id")
    private Technology technology;

    @ManyToOne
    private JobOffer jobOffer;

    public JobTechnology() {
    }

    public JobTechnology(Technology technology) {
        this.technology = technology;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Technology getTechnology() {
        return technology;
    }

    public void setTechnology(Technology technology) {
        this.technology = technology;
    }

    public JobOffer getJobOffer() {
        return jobOffer;
    }

    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }
}

