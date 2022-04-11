package JobBoard.model;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="job_offer")
public class JobOffer {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="title")
    private String title;

    @Column(name="company_name")
    private String companyName;

    @Column(name="description")
    private String description;

    @Column(name="technology_profile")
    private String technologyProfile;

    @Valid
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_offer_id")
    private List<JobRequirement> requirements = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "job_offer_id")
    private List<JobTechnology> technologies = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnologyProfile() {
        return technologyProfile;
    }

    public void setTechnologyProfile(String technologyProfile) {
        this.technologyProfile = technologyProfile;
    }

    public List<JobRequirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(List<JobRequirement> requirements) {
        this.requirements = requirements;
    }

    public void addRequirements(JobRequirement jobRequirement){
        if(requirements == null)
            requirements = new ArrayList<>();
        requirements.add(jobRequirement);
    }

    public List<JobTechnology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<JobTechnology> technologies) {
        this.technologies = technologies;
    }
}
