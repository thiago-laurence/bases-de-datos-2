package unlp.info.bd2.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "purchases")
public class Purchase {

    @Id
    private String id;

    @Indexed(unique = true)
    private String code;

    @Field("total_price")
    private float totalPrice;

    @Field
    private Date date;

    @DBRef(lazy = false)
    private User user;

    @DBRef(lazy = true)
    private Route route;

    @DBRef(lazy = true)
    private Review review;

    @DBRef(lazy = false)
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
