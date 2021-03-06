package JobBoard.model;

import javax.persistence.*;

@Entity
@Table(name="technology_profile")
public class TechnologyProfile {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(name="name")
    private String name;

    public TechnologyProfile(){
    }

    public TechnologyProfile(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
