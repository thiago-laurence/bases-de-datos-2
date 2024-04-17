package unlp.info.bd2.model;

import jakarta.persistence.*;

@Entity
public class Stop {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    @Column
    private String description;

    public Stop(){ }

    public Stop(String name, String description) {
        this.setName(name);
        this.setDescription(description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
