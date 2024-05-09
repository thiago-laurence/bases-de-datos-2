package unlp.info.bd2.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class Stop {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;
    @Column(nullable = true, length = 500)
    private String description;

    @ManyToMany(mappedBy = "stops", fetch = FetchType.LAZY)
    private List<Route> routes;

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
