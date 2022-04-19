package JobBoard.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="candidate_technology")
public class CandidateTechnology {

    public enum Level {
        SOME_EXPERIENCE, SIGNIFICANT_EXPERIENCE, EXPERTISE, ADVANCED_SKILL;
    }

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Technology can not be blank")
    @Column(name="technology_name")
    private String technologyName;

    @Column(name="skill_level")
    @Enumerated(EnumType.ORDINAL)
    private Level level;

    @ManyToOne
    @JoinColumn(name="username")
    private CandidateProfile candidateProfile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public CandidateProfile getCandidateProfile() {
        return candidateProfile;
    }

    public void setCandidateProfile(CandidateProfile candidateProfile) {
        this.candidateProfile = candidateProfile;
    }
}
