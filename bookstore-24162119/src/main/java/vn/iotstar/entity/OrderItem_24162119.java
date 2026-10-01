package vn.iotstar.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "store_order_items")
public class OrderItem_24162119 {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_id", nullable = false)
    private Order_24162119 order;
    // Snapshot fields keep the invoice unchanged when a book is edited or deleted.
    @Column(name = "bookid", nullable = false)
    private Integer bookId;
    @Column(name = "book_title", nullable = false, length = 255)
    private String bookTitle;
    @Column(name = "unit_price", nullable = false, precision = 18, scale = 2)
    private BigDecimal unitPrice;
    @Column(nullable = false)
    private int quantity;

    public OrderItem_24162119() { }
    public OrderItem_24162119(Order_24162119 order, Book_24162119 book, int quantity) {
        this.order = order; this.bookId = book.getBookid(); this.bookTitle = book.getTitle();
        this.unitPrice = book.getPrice(); this.quantity = quantity;
    }
    public Long getId() { return id; }
    public Integer getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public int getQuantity() { return quantity; }
    public BigDecimal getSubtotal() { return unitPrice.multiply(BigDecimal.valueOf(quantity)); }
}
