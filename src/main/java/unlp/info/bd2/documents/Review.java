package unlp.info.bd2.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.*;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    @Field
    private int rating;

    @Field
    private String comment;

    @DBRef(lazy = false)
    private Purchase purchase;

    public Review() { }

    public Review(int rating, String comment, Purchase purchase) {
        this.setRating(rating);
        this.setComment(comment);
        this.setPurchase(purchase);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

}