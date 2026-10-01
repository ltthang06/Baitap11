package vn.iotstar.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Supplier;
import jakarta.persistence.*;
import vn.iotstar.entity.*;
import vn.iotstar.model.*;
import vn.iotstar.util.JpaUtil_24162119;

public class CommerceService_24162119 {
    private final Supplier<EntityManager> entityManagers;
    public CommerceService_24162119() { this(JpaUtil_24162119::getEntityManager); }
    public CommerceService_24162119(Supplier<EntityManager> entityManagers) { this.entityManagers = entityManagers; }

    private <T> T transaction(Integer userId, Function<EntityManager, T> action) {
        EntityManager em = entityManagers.get();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // Serialize changes to one user's cart, including simultaneous checkout requests.
            if (userId == null || em.find(User_24162119.class, userId, LockModeType.PESSIMISTIC_WRITE) == null)
                throw new IllegalArgumentException("Tài khoản không tồn tại. Vui lòng đăng nhập lại.");
            T result = action.apply(em);
            tx.commit();
            return result;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally { em.close(); }
    }

    private List<CartItem_24162119> cartItems(EntityManager em, Integer userId) {
        return em.createQuery("SELECT c FROM CartItem_24162119 c JOIN FETCH c.book WHERE c.userId = :user ORDER BY c.book.bookid", CartItem_24162119.class)
                .setParameter("user", userId).getResultList();
    }

    public CartView_24162119 getCart(Integer userId) {
        EntityManager em = entityManagers.get();
        try { return new CartView_24162119(cartItems(em, userId)); }
        finally { em.close(); }
    }

    public void add(Integer userId, Integer bookId, int quantity) { change(userId, bookId, quantity, true); }
    public void update(Integer userId, Integer bookId, int quantity) { change(userId, bookId, quantity, false); }

    private void change(Integer userId, Integer bookId, int quantity, boolean adding) {
        if (bookId == null || bookId < 1 || quantity < 1 || quantity > 99)
            throw new IllegalArgumentException("Số lượng phải là số nguyên từ 1 đến 99.");
        transaction(userId, em -> {
            Book_24162119 book = em.find(Book_24162119.class, bookId, LockModeType.PESSIMISTIC_WRITE);
            if (book == null) throw new IllegalArgumentException("Sách không tồn tại.");
            List<CartItem_24162119> found = em.createQuery("SELECT c FROM CartItem_24162119 c WHERE c.userId = :user AND c.book.bookid = :book", CartItem_24162119.class)
                    .setParameter("user", userId).setParameter("book", bookId).getResultList();
            CartItem_24162119 item = found.isEmpty() ? null : found.get(0);
            if (!adding && item == null) throw new IllegalArgumentException("Sách không có trong giỏ hàng.");
            int desired = adding && item != null ? item.getQuantity() + quantity : quantity;
            int max = Math.min(99, book.getQuantity() == null ? 0 : Math.max(0, book.getQuantity()));
            if (desired > max) throw new IllegalArgumentException("Số lượng vượt giới hạn. Sách này được mua tối đa " + max + " cuốn.");
            if (book.getPrice() == null || book.getPrice().signum() < 0)
                throw new IllegalArgumentException("Sách chưa có giá hợp lệ.");
            if (item == null) em.persist(new CartItem_24162119(userId, book, desired));
            else item.setQuantity(desired);
            return null;
        });
    }

    public void remove(Integer userId, Integer bookId) {
        if (bookId == null || bookId < 1) throw new IllegalArgumentException("Mã sách không hợp lệ.");
        transaction(userId, em -> {
            em.createQuery("DELETE FROM CartItem_24162119 c WHERE c.userId = :user AND c.book.bookid = :book")
                    .setParameter("user", userId).setParameter("book", bookId).executeUpdate();
            return null;
        });
    }

    public void clear(Integer userId) {
        transaction(userId, em -> {
            em.createQuery("DELETE FROM CartItem_24162119 c WHERE c.userId = :user").setParameter("user", userId).executeUpdate();
            return null;
        });
    }

    public Order_24162119 checkout(Integer userId, CheckoutForm_24162119 form, String token) {
        form.validate();
        try { if (token == null || !UUID.fromString(token).toString().equals(token)) throw new IllegalArgumentException(); }
        catch (IllegalArgumentException e) { throw new IllegalArgumentException("Phiên thanh toán không hợp lệ. Vui lòng mở lại trang thanh toán."); }
        return transaction(userId, em -> {
            List<Order_24162119> existing = em.createQuery("SELECT o FROM Order_24162119 o WHERE o.userId = :user AND o.checkoutToken = :token", Order_24162119.class)
                    .setParameter("user", userId).setParameter("token", token).getResultList();
            if (!existing.isEmpty()) { existing.get(0).getItems().size(); return existing.get(0); }
            List<CartItem_24162119> cart = cartItems(em, userId);
            if (cart.isEmpty()) throw new IllegalArgumentException("Giỏ hàng trống. Vui lòng thêm sách trước khi đặt hàng.");
            Order_24162119 order = new Order_24162119(userId, form.getRecipientName(), form.getPhone(), form.getAddress(), form.getNote(), token);
            BigDecimal total = BigDecimal.ZERO;
            // Lock in book ID order to prevent two customers from buying the same final copy.
            for (CartItem_24162119 item : cart) {
                Book_24162119 book = item.getBook();
                em.refresh(book, LockModeType.PESSIMISTIC_WRITE);
                if (!item.isAvailable()) throw new IllegalArgumentException("Sách '" + book.getTitle() + "' không đủ tồn kho hoặc chưa có giá hợp lệ. Vui lòng sửa giỏ hàng.");
                OrderItem_24162119 line = new OrderItem_24162119(order, book, item.getQuantity());
                order.addItem(line);
                total = total.add(line.getSubtotal());
                book.setQuantity(book.getQuantity() - item.getQuantity());
            }
            order.setTotal(total);
            em.persist(order);
            cart.forEach(em::remove);
            return order;
        });
    }

    public List<Order_24162119> findOrders(Integer userId, OrderStatus_24162119 status) {
        EntityManager em = entityManagers.get();
        try {
            String jpql = "SELECT o FROM Order_24162119 o WHERE o.userId = :user";
            if (status != null) jpql += " AND o.status = :status";
            TypedQuery<Order_24162119> query = em.createQuery(jpql + " ORDER BY o.createdAt DESC, o.id DESC", Order_24162119.class).setParameter("user", userId);
            if (status != null) query.setParameter("status", status);
            return query.getResultList();
        } finally { em.close(); }
    }

    public Order_24162119 findOrder(Integer userId, Long orderId) {
        EntityManager em = entityManagers.get();
        try {
            List<Order_24162119> orders = em.createQuery("SELECT DISTINCT o FROM Order_24162119 o LEFT JOIN FETCH o.items WHERE o.userId = :user AND o.id = :id", Order_24162119.class)
                    .setParameter("user", userId).setParameter("id", orderId).getResultList();
            return orders.isEmpty() ? null : orders.get(0);
        } finally { em.close(); }
    }
}
