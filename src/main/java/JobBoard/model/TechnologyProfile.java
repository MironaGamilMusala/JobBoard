package JobBoard.model;

import javax.persistence.*;

@Entity
@Table(name="technology_profile")
public class TechnologyProfile {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="technology_profile_name")
    private String technologyProfileName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTechnologyProfileName() {
        return technologyProfileName;
    }

    public void setTechnologyProfileName(String technologyProfileName) {
        this.technologyProfileName = technologyProfileName;
    }
}
