package vn.iotstar.model;

import java.math.BigDecimal;
import java.util.List;
import vn.iotstar.entity.CartItem_24162119;

public class CartView_24162119 {
    private final List<CartItem_24162119> items;
    public CartView_24162119(List<CartItem_24162119> items) { this.items = List.copyOf(items); }
    public List<CartItem_24162119> getItems() { return items; }
    public boolean isEmpty() { return items.isEmpty(); }
    public int getItemCount() { return items.stream().mapToInt(CartItem_24162119::getQuantity).sum(); }
    public BigDecimal getTotal() { return items.stream().map(CartItem_24162119::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add); }
    public boolean isCheckoutAllowed() { return !items.isEmpty() && items.stream().allMatch(CartItem_24162119::isAvailable); }
}
