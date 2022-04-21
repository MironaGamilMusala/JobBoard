package JobBoard.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="job_offer")
public class JobOffer {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotBlank (message = "Title can not be blank")
    @Column(name="title")
    private String title;

    @NotBlank (message = "Company Name can not be blank")
    @Column(name="company_name")
    private String companyName;

    @NotBlank (message = "Description can not be blank")
    @Column(name="description")
    private String description;

    @NotBlank (message = "Technology Profile can not be blank")
    @Column(name="technology_profile")
    private String technologyProfile;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "job_offer_id")
    @NotEmpty(message = "Requirements can not be empty")
    private List<@Valid JobRequirement> requirements = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "job_offer_id")
    @NotEmpty(message = "Technologies can not be empty")
    private List<@Valid JobTechnology> technologies = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "job_candidate", joinColumns = @JoinColumn(name="job_offer_id"),
            inverseJoinColumns = @JoinColumn(name="username"))
    private List<CandidateProfile> appliedCandidates = new ArrayList<>();

    public JobOffer() {
    }

    public JobOffer(String title, String companyName, String description, String technologyProfile,
                    List<@Valid JobRequirement> requirements, List<@Valid JobTechnology> technologies) {
        this.title = title;
        this.companyName = companyName;
        this.description = description;
        this.technologyProfile = technologyProfile;
        this.requirements = requirements;
        this.technologies = technologies;
    }

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

    public List<JobTechnology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<JobTechnology> technologies) {
        this.technologies = technologies;
    }

    public List<CandidateProfile> getAppliedCandidates() {
        return appliedCandidates;
    }

    public void setAppliedCandidates(List<CandidateProfile> appliedCandidates) {
        this.appliedCandidates = appliedCandidates;
    }
}
