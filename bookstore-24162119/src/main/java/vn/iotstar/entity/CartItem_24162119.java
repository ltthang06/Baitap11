package vn.iotstar.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "cart_items", uniqueConstraints = @UniqueConstraint(columnNames = {"userid", "bookid"}))
public class CartItem_24162119 {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userid", nullable = false)
    private Integer userId;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "bookid", nullable = false)
    private Book_24162119 book;
    @Column(nullable = false)
    private int quantity;

    public CartItem_24162119() { }
    public CartItem_24162119(Integer userId, Book_24162119 book, int quantity) {
        this.userId = userId; this.book = book; this.quantity = quantity;
    }
    public Long getId() { return id; }
    public Integer getUserId() { return userId; }
    public Book_24162119 getBook() { return book; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getMaxQuantity() { return Math.min(99, book.getQuantity() == null ? 0 : Math.max(0, book.getQuantity())); }
    public boolean isAvailable() { return quantity > 0 && quantity <= getMaxQuantity() && book.getPrice() != null && book.getPrice().signum() >= 0; }
    public BigDecimal getSubtotal() {
        return book.getPrice() == null ? BigDecimal.ZERO : book.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
