package unlp.info.bd2.model;

import jakarta.persistence.*;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rating;
    @Column(nullable = false)
    private String comment;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "purchase_id", nullable = false)
    private Purchase purchase;

    public Review() { }

    public Review(int rating, String comment, Purchase purchase) {
        this.setRating(rating);
        this.setComment(comment);
        this.setPurchase(purchase);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
