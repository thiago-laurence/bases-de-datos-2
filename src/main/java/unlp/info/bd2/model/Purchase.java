package unlp.info.bd2.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private float totalPrice;

    private Date date;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", referencedColumnName = "id", nullable = false)
    private Route route;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    private Review review;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "purchase", cascade = CascadeType.ALL)
    private List<ItemService> itemServiceList;

    public Purchase(){ }

    public Purchase(String code, Date date, Route route, User user) {
        this.setCode(code);
        this.setDate(date);
        this.setRoute(route);
        this.setUser(user);
        this.itemServiceList = new ArrayList<>();

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

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

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
        this.itemServiceList.add(itemService);
        itemService.setPurchase(this);
    }

//    public Review getReview() {
//        return review;
//    }

//    public void setReview(Review review) {
//        this.review = review;
//    }

//
}
