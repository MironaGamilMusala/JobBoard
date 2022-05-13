package JobBoard.model;

import javax.persistence.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="candidate_profile")
public class CandidateProfile {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="previous_experience")
    private String previousExperience;

    @OneToOne
    @JoinColumn(name="technology_profile_id")
    private TechnologyProfile technologyProfile;

    @OneToOne(cascade = CascadeType.PERSIST)
    private CustomUser user;

    @OneToMany(mappedBy="candidateProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<@Valid CandidateTechnology> technologies = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name = "job_candidate", joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="job_offer_id"))
    private List<JobOffer> appliedJobs = new ArrayList<>();

    public CandidateProfile(){

    }
    public CandidateProfile(CustomUser user){
        this.user=user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreviousExperience() {
        return previousExperience;
    }

    public void setPreviousExperience(String previousExperience) {
        this.previousExperience = previousExperience;
    }

    public TechnologyProfile getTechnologyProfile() {
        return technologyProfile;
    }

    public void setTechnologyProfile(TechnologyProfile technologyProfile) {
        this.technologyProfile = technologyProfile;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
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
