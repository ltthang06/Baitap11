# Kết quả kiểm thử — 01/10/2026

## Build và kiểm thử dịch vụ

`mvn clean verify` thành công với Java 17. 8 kiểm thử JUnit/H2 qua:

- Giỏ hàng lưu trong database; thêm cùng sách cộng số lượng; sửa/xóa/xóa hết; tính tổng chính xác và phân tách tài khoản.
- Sách không tồn tại hoặc hết hàng bị từ chối.
- Checkout dùng giá mới nhất, giữ thông tin lúc mua, tạo COD/NEW, trừ tồn kho, xóa giỏ; gửi lại không trừ tồn kho lần nữa.
- Tồn kho thay đổi trước checkout: rollback cả đơn và tồn kho, giữ giỏ hàng.
- Giỏ trống, thông tin giao hàng sai, token không hợp lệ bị từ chối.
- Đổi trạng thái bằng SQL: cả 8 bộ lọc trả đúng đơn của tài khoản.
- Hai tài khoản cùng đặt cuốn cuối: chỉ một đơn thành công, tồn kho không âm.
- Hai yêu cầu checkout cùng token chạy đồng thời: chỉ tạo một đơn.

## Kiểm tra Tomcat và SQL Server thật

Chạy WAR trên Tomcat 10.1.44, dùng database kiểm thử riêng `BookStore_24162119_CodexQA`, không sửa database bài cũ. 48 kiểm tra HTTP qua, gồm:

- Đăng nhập, các trang home/book-detail/admin-books/giỏ/checkout/lịch sử/chi tiết biên dịch JSP và trả HTTP 200.
- Chưa đăng nhập được chuyển về login; request sửa giỏ thiếu CSRF trả 403.
- Thêm, cộng, sửa, xóa, xóa hết và số lượng 0/âm/thập phân/quá 99/quá số nguyên/quá tồn kho/hết hàng.
- Hai tài khoản không thấy giỏ hoặc đơn của nhau; chi tiết đơn tài khoản khác trả 404.
- Server kiểm tra điện thoại, chỉ chấp nhận COD, giữ thông tin khi báo lỗi.
- Đơn COD lưu Unicode, số điện thoại giữ số 0 đầu, giá/tổng tiền chính xác, giảm kho và xóa giỏ.
- Gửi POST COD lần nữa trả cùng mã đơn, không tạo đơn trùng.
- Nội dung ghi chú dạng HTML được escape khi hiển thị.
- SQL UPDATE lần lượt NEW/CONFIRMED/PREPARING/SHIPPING/DELIVERING/DELIVERED/CANCELLED/RETURNED: bộ lọc HTTP phản ánh đúng từng trạng thái và không lộ đơn tài khoản khác.
- Trạng thái/mã đơn không hợp lệ trả 400; đơn không tồn tại trả 404.

Đã thao tác trên trình duyệt: đăng nhập User, thêm sách, sửa số lượng, đặt COD, xem chi tiết và lọc Đã xác nhận sau SQL UPDATE.

Đăng ký OTP qua email không nằm trong lần kiểm thử này vì cần thông tin SMTP của người dùng. Việc push GitHub và nộp UTExLMS do người dùng thực hiện.
