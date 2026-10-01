package vn.iotstar.service;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.Consumer;
import jakarta.persistence.*;
import org.junit.jupiter.api.*;
import vn.iotstar.entity.*;
import vn.iotstar.model.CheckoutForm_24162119;

class CommerceServiceTest {
    private static EntityManagerFactory factory;
    private CommerceService_24162119 service;
    private Integer userA, userB, bookId, otherBookId;
    private final CheckoutForm_24162119 form = new CheckoutForm_24162119("Lê Thành Thắng", "0901234567", "1 Võ Văn Ngân, Thủ Đức, TP.HCM", "Giao giờ hành chính");

    @BeforeAll static void open() { factory = Persistence.createEntityManagerFactory("CommerceTestPU"); }
    @AfterAll static void close() { factory.close(); }
    @BeforeEach void setup() {
        service = new CommerceService_24162119(factory::createEntityManager);
        inTransaction(em -> {
            em.createQuery("DELETE FROM OrderItem_24162119").executeUpdate();
            em.createQuery("DELETE FROM Order_24162119").executeUpdate();
            em.createQuery("DELETE FROM CartItem_24162119").executeUpdate();
            em.createQuery("DELETE FROM Book_24162119").executeUpdate();
            em.createQuery("DELETE FROM User_24162119").executeUpdate();
            User_24162119 a = user("a@example.com"), b = user("b@example.com");
            em.persist(a); em.persist(b); userA = a.getId(); userB = b.getId();
            Book_24162119 book = book("Sách Tiếng Việt", 5, "125000.00"), other = book("Sách thứ hai", 3, "95000.00");
            em.persist(book); em.persist(other); bookId = book.getBookid(); otherBookId = other.getBookid();
        });
    }
    private User_24162119 user(String email) {
        User_24162119 user = new User_24162119(); user.setEmail(email); user.setPasswd("test"); return user;
    }
    private Book_24162119 book(String title, int stock, String price) {
        Book_24162119 book = new Book_24162119(); book.setTitle(title); book.setQuantity(stock); book.setPrice(new BigDecimal(price)); return book;
    }
    private void inTransaction(Consumer<EntityManager> action) {
        EntityManager em = factory.createEntityManager();
        try { em.getTransaction().begin(); action.accept(em); em.getTransaction().commit(); }
        finally { if (em.getTransaction().isActive()) em.getTransaction().rollback(); em.close(); }
    }
    private int stock(Integer id) {
        EntityManager em = factory.createEntityManager();
        try { return em.find(Book_24162119.class, id).getQuantity(); } finally { em.close(); }
    }

    @Test void cartPersistsAndAddsUpdatesRemovesWithinStock() {
        service.add(userA, bookId, 2); service.add(userA, bookId, 1);
        assertEquals(3, new CommerceService_24162119(factory::createEntityManager).getCart(userA).getItemCount());
        assertEquals(new BigDecimal("375000.00"), service.getCart(userA).getTotal());
        assertThrows(IllegalArgumentException.class, () -> service.add(userA, bookId, 3));
        for (int quantity : new int[]{0, -1, 6, 100, Integer.MAX_VALUE})
            assertThrows(IllegalArgumentException.class, () -> service.update(userA, bookId, quantity));
        service.update(userA, bookId, 5); assertEquals(5, service.getCart(userA).getItemCount());
        service.remove(userB, bookId); assertEquals(5, service.getCart(userA).getItemCount());
        service.remove(userA, bookId); assertTrue(service.getCart(userA).isEmpty());
        assertThrows(IllegalArgumentException.class, () -> service.update(userA, bookId, 1));
        service.add(userA, bookId, 1); service.add(userA, otherBookId, 2);
        service.clear(userB); assertEquals(3, service.getCart(userA).getItemCount());
        service.clear(userA); assertTrue(service.getCart(userA).isEmpty());
    }

    @Test void outOfStockAndUnknownBooksCannotBeAdded() {
        inTransaction(em -> em.find(Book_24162119.class, bookId).setQuantity(0));
        assertThrows(IllegalArgumentException.class, () -> service.add(userA, bookId, 1));
        assertThrows(IllegalArgumentException.class, () -> service.add(userA, -1, 1));
        assertThrows(IllegalArgumentException.class, () -> service.add(userA, Integer.MAX_VALUE, 1));
        assertTrue(service.getCart(userA).isEmpty());
    }

    @Test void codCheckoutUsesLatestPriceSnapshotsClearsCartAndDeductsStockOnce() {
        service.add(userA, bookId, 2); service.add(userA, otherBookId, 1);
        inTransaction(em -> em.find(Book_24162119.class, bookId).setPrice(new BigDecimal("130000.00")));
        String token = UUID.randomUUID().toString();
        Order_24162119 order = service.checkout(userA, form, token);
        assertEquals(new BigDecimal("355000.00"), order.getTotal());
        assertEquals(OrderStatus_24162119.NEW, order.getStatus()); assertEquals("COD", order.getPaymentMethod());
        assertEquals("0901234567", order.getPhone()); assertEquals("Lê Thành Thắng", order.getRecipientName());
        assertEquals(2, order.getItems().size()); assertTrue(service.getCart(userA).isEmpty());
        assertEquals(3, stock(bookId)); assertEquals(2, stock(otherBookId));
        assertEquals(order.getId(), service.checkout(userA, form, token).getId());
        assertEquals(3, stock(bookId)); assertEquals(1, service.findOrders(userA, null).size());
        inTransaction(em -> { Book_24162119 book = em.find(Book_24162119.class, bookId); book.setTitle("Tên đã sửa"); book.setPrice(new BigDecimal("500000.00")); });
        Order_24162119 saved = service.findOrder(userA, order.getId());
        assertEquals("Sách Tiếng Việt", saved.getItems().get(0).getBookTitle());
        assertEquals(new BigDecimal("130000.00"), saved.getItems().get(0).getUnitPrice());
        assertEquals(new BigDecimal("355000.00"), saved.getTotal());
        assertNull(service.findOrder(userB, order.getId())); assertTrue(service.findOrders(userB, null).isEmpty());
    }

    @Test void stockChangeRollsBackWholeCheckoutAndRetainsCart() {
        service.add(userA, bookId, 2); service.add(userA, otherBookId, 3);
        inTransaction(em -> em.find(Book_24162119.class, otherBookId).setQuantity(1));
        assertFalse(service.getCart(userA).isCheckoutAllowed());
        assertThrows(IllegalArgumentException.class, () -> service.checkout(userA, form, UUID.randomUUID().toString()));
        assertEquals(5, stock(bookId)); assertEquals(1, stock(otherBookId));
        assertEquals(5, service.getCart(userA).getItemCount()); assertTrue(service.findOrders(userA, null).isEmpty());
    }

    @Test void emptyCartAndInvalidDeliveryInformationAreRejected() {
        assertThrows(IllegalArgumentException.class, () -> service.checkout(userA, form, UUID.randomUUID().toString()));
        service.add(userA, bookId, 1);
        for (CheckoutForm_24162119 invalid : List.of(
                new CheckoutForm_24162119("", "0901234567", "Địa chỉ hợp lệ", ""),
                new CheckoutForm_24162119("Người nhận", "901234567", "Địa chỉ hợp lệ", ""),
                new CheckoutForm_24162119("Người nhận", "0901234567", "", ""),
                new CheckoutForm_24162119("Người nhận", "0901234567", "Địa chỉ hợp lệ", "a".repeat(501))))
            assertThrows(IllegalArgumentException.class, () -> service.checkout(userA, invalid, UUID.randomUUID().toString()));
        assertThrows(IllegalArgumentException.class, () -> service.checkout(userA, form, "invalid"));
        assertEquals(5, stock(bookId)); assertEquals(1, service.getCart(userA).getItemCount());
    }

    @Test void databaseStatusChangesAppearInAllEightFilters() {
        service.add(userA, bookId, 1);
        Long id = service.checkout(userA, form, UUID.randomUUID().toString()).getId();
        for (OrderStatus_24162119 status : OrderStatus_24162119.values()) {
            inTransaction(em -> em.createNativeQuery("UPDATE store_orders SET status = ? WHERE id = ?").setParameter(1, status.name()).setParameter(2, id).executeUpdate());
            assertEquals(1, service.findOrders(userA, status).size());
            assertEquals(status, service.findOrder(userA, id).getStatus());
            assertTrue(service.findOrders(userB, status).isEmpty());
            for (OrderStatus_24162119 other : OrderStatus_24162119.values())
                if (other != status) assertTrue(service.findOrders(userA, other).isEmpty());
        }
    }

    @Test void simultaneousCustomersCannotBuyTheSameLastCopy() throws Exception {
        inTransaction(em -> em.find(Book_24162119.class, bookId).setQuantity(1));
        service.add(userA, bookId, 1); service.add(userB, bookId, 1);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch start = new CountDownLatch(1);
        try {
            List<Future<Boolean>> results = new ArrayList<>();
            for (Integer user : List.of(userA, userB)) results.add(executor.submit(() -> {
                start.await();
                try { service.checkout(user, form, UUID.randomUUID().toString()); return true; }
                catch (IllegalArgumentException e) { return false; }
            }));
            start.countDown();
            int successes = 0;
            for (Future<Boolean> result : results) if (result.get(15, TimeUnit.SECONDS)) successes++;
            assertEquals(1, successes); assertEquals(0, stock(bookId));
            assertEquals(1, service.findOrders(userA, null).size() + service.findOrders(userB, null).size());
        } finally { executor.shutdownNow(); }
    }

    @Test void simultaneousDuplicateCheckoutCreatesOnlyOneOrder() throws Exception {
        service.add(userA, bookId, 2);
        String token = UUID.randomUUID().toString();
        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            Future<Long> a = executor.submit(() -> service.checkout(userA, form, token).getId());
            Future<Long> b = executor.submit(() -> service.checkout(userA, form, token).getId());
            assertEquals(a.get(15, TimeUnit.SECONDS), b.get(15, TimeUnit.SECONDS));
            assertEquals(1, service.findOrders(userA, null).size()); assertEquals(3, stock(bookId));
        } finally { executor.shutdownNow(); }
    }
}
