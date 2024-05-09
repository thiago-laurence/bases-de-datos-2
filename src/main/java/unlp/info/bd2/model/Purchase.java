package unlp.info.bd2.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 12)
    private String code;

    @Column(name = "total_price", nullable = false, length = 20)
    private float totalPrice;

    @Column(nullable = false, length = 10)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REFRESH })
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "purchase", 
        cascade = { CascadeType.REMOVE, CascadeType.MERGE, CascadeType.REFRESH }, orphanRemoval = true)
    private Review review;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "purchase", 
        cascade = { CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.MERGE }, orphanRemoval = true)
    private List<ItemService> itemServiceList;

    public Purchase(){ }

    public Purchase(String code, Date date, Route route, User user) {
        this.setCode(code);
        this.setDate(date);
        this.setRoute(route);
        this.setUser(user);
        this.setTotalPrice(route.getPrice());
        this.setItemServiceList(new ArrayList<ItemService>());
        this.setReview(null);
    }

    public Purchase(String code, Route route, User user) {
        this.setCode(code);
        this.setRoute(route);
        this.setUser(user);
        this.setDate(new Date());
        this.setTotalPrice(route.getPrice());
        this.setItemServiceList(new ArrayList<ItemService>());
        this.setReview(null);
    }       

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) { this.totalPrice = totalPrice; }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<ItemService> getItemServiceList() {
        return itemServiceList;
    }

    public void setItemServiceList(List<ItemService> itemServiceList) {
        this.itemServiceList = itemServiceList;
    }

    public void addItemService(ItemService itemService) {
        if (!this.getItemServiceList().contains(itemService)) {
            this.getItemServiceList().add(itemService);
            this.setTotalPrice(this.getTotalPrice() + itemService.getTotalPrice());
        }
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) { this.review = review; }
}