package vn.iotstar.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "rating")
public class Rating_24162119 {

    @EmbeddedId
    private RatingId_24162119 id;

    @Column(name = "rating")
    private Byte rating;

    @Column(
            name = "review_text",
            columnDefinition = "text"
    )
    private String reviewText;

    @ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(
            name = "userid",
            insertable = false,
            updatable = false
    )
    private User_24162119 user;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(
            name = "bookid",
            insertable = false,
            updatable = false
    )
    private Book_24162119 book;

    public Rating_24162119() {
    }

    public RatingId_24162119
            getId() {
        return id;
    }

    public void setId(
            RatingId_24162119 id) {
        this.id = id;
    }

    public Byte getRating() {
        return rating;
    }

    public void setRating(
            Byte rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(
            String reviewText) {
        this.reviewText =
                reviewText;
    }

    public User_24162119
            getUser() {
        return user;
    }

    public Book_24162119
            getBook() {
        return book;
    }
}