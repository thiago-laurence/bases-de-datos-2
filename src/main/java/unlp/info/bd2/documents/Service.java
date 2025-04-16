package unlp.info.bd2.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "services")
public class Service {

    @Id
    private String id;

    @Field
    private String name;

    @Field
    private float price;

    @Field
    private String description;

    @DBRef(lazy = true)
    private List<ItemService> items;

    @DBRef(lazy = false)
    private Supplier supplier;

    public Service(){ }

    public Service(String name, float price, String description) {
        this.setName(name);
        this.setPrice(price);
        this.setDescription(description);
        this.setItems(new ArrayList<ItemService>());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
