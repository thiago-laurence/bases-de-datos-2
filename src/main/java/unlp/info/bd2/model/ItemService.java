package unlp.info.bd2.model;

import jakarta.persistence.*;

@Entity
public class ItemService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private int quantity;

    @ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.REFRESH })
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    public ItemService() {}

    public ItemService(int quantity, Purchase purchase, Service service) {
        this.setQuantity(quantity);
        this.setPurchase(purchase);
        this.setService(service);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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