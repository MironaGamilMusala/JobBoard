package JobBoard.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

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

    @Column(name="skill_level")
    @Enumerated(EnumType.STRING)
    private Level level;

    @OneToOne
    @JoinColumn(name = "technology_id")
    private Technology technology;

    @ManyToOne
    @JoinColumn(name="user_id")
    private CandidateProfile candidateProfile;

    public CandidateTechnology() {
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
