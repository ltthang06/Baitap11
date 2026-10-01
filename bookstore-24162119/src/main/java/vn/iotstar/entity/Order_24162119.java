package vn.iotstar.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "store_orders", uniqueConstraints = @UniqueConstraint(columnNames = "checkout_token"))
public class Order_24162119 {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userid", nullable = false)
    private Integer userId;
    @Column(name = "recipient_name", nullable = false, length = 100)
    private String recipientName;
    @Column(nullable = false, length = 20)
    private String phone;
    @Column(nullable = false, length = 500)
    private String address;
    @Column(length = 500)
    private String note;
    @Column(name = "payment_method", nullable = false, length = 10)
    private String paymentMethod = "COD";
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus_24162119 status = OrderStatus_24162119.NEW;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal total;
    @Column(name = "checkout_token", nullable = false, length = 36)
    private String checkoutToken;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("id ASC")
    private List<OrderItem_24162119> items = new ArrayList<>();

    public Order_24162119() { }
    public Order_24162119(Integer userId, String name, String phone, String address, String note, String token) {
        this.userId = userId; this.recipientName = name; this.phone = phone;
        this.address = address; this.note = note; this.checkoutToken = token;
    }
    public Long getId() { return id; }
    public Integer getUserId() { return userId; }
    public String getRecipientName() { return recipientName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getNote() { return note; }
    public String getPaymentMethod() { return paymentMethod; }
    public OrderStatus_24162119 getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getCreatedAtText() { return createdAt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")); }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public List<OrderItem_24162119> getItems() { return items; }
    public void addItem(OrderItem_24162119 item) { items.add(item); }
}
