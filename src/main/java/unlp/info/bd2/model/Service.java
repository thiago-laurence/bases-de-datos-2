package unlp.info.bd2.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private float price;
    @Column
    private String description;
    @OneToMany(mappedBy = "service", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ItemService> items;
    @ManyToOne
    private Supplier supplier;


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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ItemService> getItems() {
        return items;
    }

    public void setItems(List<ItemService> itemServiceList) {
        this.items = itemServiceList;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Service(){
        this.items = new ArrayList<ItemService>();
    }

    public Service(String name, float price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.items = new ArrayList<ItemService>();
    }
}
