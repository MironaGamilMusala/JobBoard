package JobBoard.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="candidate_profile")
public class CandidateProfile {

    public enum TechnologyFocus {
        BACKEND, FRONTEND, FULLSTACK;
    }

    public CandidateProfile(String username){
        this.username = username;
        this.previousExperience = null;
        this.technologyFocus = null;
        this.technologies = new ArrayList<>();
    }

    public CandidateProfile(){

    }

    @Id
    @Column(name="username")
    private String username;

    @Column(name="previous_experience")
    private String previousExperience;

    @Column(name="technology_focus")
    @Enumerated(EnumType.ORDINAL)
    private TechnologyFocus technologyFocus;

    @OneToMany(mappedBy="candidateProfile", cascade = CascadeType.ALL)
    private List<CandidateTechnology> technologies;

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
}
