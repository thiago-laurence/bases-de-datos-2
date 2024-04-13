package unlp.info.bd2.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int quantity;
    //@ManyToOne
    //private Purchase purchase;
    @ManyToOne
    private Service service;

    
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

    //public Purchase getPurchase() {
    //    return purchase;
    //}

    //public void setPurchase(Purchase purchase) {
    //    this.purchase = purchase;
    //}

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
