package unlp.info.bd2.models;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueServicePerSupplier", columnNames = { "name", "description", "supplier_id" }) })
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;
    @Column(nullable = false, length = 10)
    private float price;
    @Column(nullable = false, length = 200)
    private String description;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private List<ItemService> items;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id", nullable = false, updatable = false)
    private Supplier supplier;

    public Service(){ }

    public Service(String name, float price, String description) {
        this.setName(name);
        this.setPrice(price);
        this.setDescription(description);
        this.setItems(new ArrayList<ItemService>());
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

    public void addItemService(ItemService itemService) {
        if (!this.items.contains(itemService))
            this.items.add(itemService);
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

}
