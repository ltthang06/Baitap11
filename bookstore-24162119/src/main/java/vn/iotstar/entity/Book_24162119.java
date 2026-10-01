package vn.iotstar.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book_24162119 {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    @Column(name = "bookid")
    private Integer bookid;

    @Column(name = "isbn")
    private Integer isbn;

    @Column(
            name = "title",
            length = 200
    )
    private String title;

    @Column(
            name = "publisher",
            length = 100
    )
    private String publisher;

    @Column(
            name = "price",
            precision = 18,
            scale = 2
    )
    private BigDecimal price;

    @Column(
            name = "description",
            columnDefinition = "text"
    )
    private String description;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(
            name = "cover_image",
            length = 100
    )
    private String coverImage;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToMany(fetch = FetchType.LAZY)

    @JoinTable(
            name = "book_author",

            joinColumns =
                    @JoinColumn(
                            name = "bookid"
                    ),

            inverseJoinColumns =
                    @JoinColumn(
                            name = "author_id"
                    )
    )
    private Set<Author_24162119>
            authors =
            new HashSet<>();

    public Book_24162119() {
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(
            Integer bookid) {
        this.bookid = bookid;
    }

    public Integer getIsbn() {
        return isbn;
    }

    public void setIsbn(
            Integer isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(
            String title) {
        this.title = title;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(
            String publisher) {
        this.publisher =
                publisher;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(
            BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description) {
        this.description =
                description;
    }

    public LocalDate
            getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(
            LocalDate publishDate) {
        this.publishDate =
                publishDate;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(
            String coverImage) {
        this.coverImage =
                coverImage;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(
            Integer quantity) {
        this.quantity = quantity;
    }

    public Set<Author_24162119>
            getAuthors() {
        return authors;
    }

    public void setAuthors(
            Set<Author_24162119>
                    authors) {

        this.authors = authors;
    }
}
