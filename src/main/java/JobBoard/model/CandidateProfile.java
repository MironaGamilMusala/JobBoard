package JobBoard.model;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="candidate_profile")
public class CandidateProfile {

    public enum TechnologyFocus {
        BACKEND, FRONTEND, FULLSTACK;
    }

    @Id
    @Column(name="username")
    private String username;

    @Column(name="previous_experience")
    private String previousExperience;

    @Column(name="technology_focus")
    @Enumerated(EnumType.ORDINAL)
    private TechnologyFocus technologyFocus;

    @OneToMany(mappedBy="candidateProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<@Valid CandidateTechnology> technologies = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "job_candidate", joinColumns = @JoinColumn(name="username"),
            inverseJoinColumns = @JoinColumn(name="job_offer_id"))
    private List<JobOffer> appliedJobs = new ArrayList<>();

    public CandidateProfile(){

    }
    public CandidateProfile(String username){
        this.username = username;
        this.previousExperience = null;
        this.technologyFocus = null;
        this.technologies = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPreviousExperience() {
        return previousExperience;
    }

    public void setPreviousExperience(String previousExperience) {
        this.previousExperience = previousExperience;
    }

    public TechnologyFocus getTechnologyFocus() {
        return technologyFocus;
    }

    public void setTechnologyFocus(TechnologyFocus technologyFocus) {
        this.technologyFocus = technologyFocus;
    }

    public List<CandidateTechnology> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<CandidateTechnology> technologies) {
        this.technologies = technologies;
    }

    public List<JobOffer> getAppliedJobs() {
        return appliedJobs;
    }

    public void setAppliedJobs(List<JobOffer> appliedJobs) {
        this.appliedJobs = appliedJobs;
    }
}
