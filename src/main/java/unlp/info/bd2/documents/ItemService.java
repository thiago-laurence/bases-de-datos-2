package unlp.info.bd2.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = "items_services")
public class ItemService {

    @Id
    private String id;

    @Field
    private int quantity;

    @DBRef(lazy = false)
    private Purchase purchase;

    @DBRef(lazy = false)
    private Service service;

    public ItemService() {}

    public ItemService(int quantity, Purchase purchase, Service service) {
        this.setQuantity(quantity);
        this.setPurchase(purchase);
        this.setService(service);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public float getTotalPrice(){
        return this.getService().getPrice() * this.getQuantity();
    }
}
