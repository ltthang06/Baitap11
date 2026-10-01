-- Chạy trong database của project. Chọn đúng mã đơn từ kết quả dưới.
SELECT id, userid, recipient_name, total, payment_method, status, created_at
FROM dbo.store_orders ORDER BY id DESC;
GO
-- Sửa @OrderId và @Status rồi chạy đoạn dưới; sau đó tải lại lịch sử/bộ lọc.
DECLARE @OrderId BIGINT = 1;
DECLARE @Status VARCHAR(20) = 'CONFIRMED';
-- NEW        : Đơn hàng mới
-- CONFIRMED  : Đã xác nhận
-- PREPARING  : Chuẩn bị hàng
-- SHIPPING   : Vận chuyển
-- DELIVERING : Giao hàng
-- DELIVERED  : Đã giao
-- CANCELLED  : Đơn hàng hủy
-- RETURNED   : Đơn hàng hoàn
IF @Status NOT IN ('NEW','CONFIRMED','PREPARING','SHIPPING','DELIVERING','DELIVERED','CANCELLED','RETURNED')
    THROW 50001, N'Trạng thái không hợp lệ.', 1;
UPDATE dbo.store_orders SET status = @Status WHERE id = @OrderId;
IF @@ROWCOUNT = 0 PRINT N'Không tìm thấy mã đơn hàng. Hãy sửa @OrderId.';
SELECT id, userid, status, total FROM dbo.store_orders WHERE id = @OrderId;
GO
-- Đổi status bằng SQL chỉ phục vụ quan sát theo đề, không tự hoàn lại tồn kho.
-- Không sửa total hoặc store_order_items: đây là thông tin hóa đơn lúc đặt.
