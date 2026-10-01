package vn.iotstar.model;

public class CheckoutForm_24162119 {
    private final String recipientName, phone, address, note;
    public CheckoutForm_24162119(String name, String phone, String address, String note) {
        this.recipientName = clean(name); this.phone = clean(phone);
        this.address = clean(address); this.note = clean(note);
    }
    private static String clean(String value) { return value == null ? "" : value.trim(); }
    public String getRecipientName() { return recipientName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getNote() { return note; }
    public void validate() {
        if (recipientName.length() < 2 || recipientName.length() > 100)
            throw new IllegalArgumentException("Họ tên người nhận phải từ 2 đến 100 ký tự.");
        if (!phone.matches("(?:0[0-9]{9}|\\+84[0-9]{9})"))
            throw new IllegalArgumentException("Số điện thoại phải gồm 10 số bắt đầu bằng 0, hoặc +84 và 9 số.");
        if (address.length() < 10 || address.length() > 500)
            throw new IllegalArgumentException("Địa chỉ giao hàng phải từ 10 đến 500 ký tự.");
        if (note.length() > 500) throw new IllegalArgumentException("Ghi chú tối đa 500 ký tự.");
    }
}
