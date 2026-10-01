-- Chạy trong database BookStore_24162119 đang dùng.
-- Chỉ thêm bảng mới; không xóa hay cập nhật dữ liệu bài cũ.
SET XACT_ABORT ON;
BEGIN TRANSACTION;
IF OBJECT_ID(N'dbo.cart_items', N'U') IS NULL
CREATE TABLE dbo.cart_items (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    userid INT NOT NULL REFERENCES dbo.users(id),
    bookid INT NOT NULL REFERENCES dbo.books(bookid),
    quantity INT NOT NULL CHECK (quantity BETWEEN 1 AND 99),
    CONSTRAINT UQ_cart_user_book UNIQUE(userid, bookid)
);
IF OBJECT_ID(N'dbo.store_orders', N'U') IS NULL
CREATE TABLE dbo.store_orders (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    userid INT NOT NULL REFERENCES dbo.users(id),
    recipient_name NVARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    address NVARCHAR(500) NOT NULL,
    note NVARCHAR(500),
    payment_method VARCHAR(10) NOT NULL DEFAULT 'COD' CHECK (payment_method = 'COD'),
    status VARCHAR(20) NOT NULL DEFAULT 'NEW'
        CHECK (status IN ('NEW','CONFIRMED','PREPARING','SHIPPING','DELIVERING','DELIVERED','CANCELLED','RETURNED')),
    created_at DATETIME2 NOT NULL DEFAULT SYSDATETIME(),
    total DECIMAL(18,2) NOT NULL CHECK (total >= 0),
    checkout_token VARCHAR(36) NOT NULL UNIQUE
);
IF OBJECT_ID(N'dbo.store_order_items', N'U') IS NULL
CREATE TABLE dbo.store_order_items (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES dbo.store_orders(id),
    -- Giữ mã/tên/giá sách lúc mua để lịch sử không thay đổi khi sửa sách.
    bookid INT NOT NULL,
    book_title NVARCHAR(255) NOT NULL,
    unit_price DECIMAL(18,2) NOT NULL CHECK (unit_price >= 0),
    quantity INT NOT NULL CHECK (quantity BETWEEN 1 AND 99)
);
IF NOT EXISTS (SELECT 1 FROM sys.indexes WHERE name = 'IX_store_orders_user_status' AND object_id = OBJECT_ID('dbo.store_orders'))
    CREATE INDEX IX_store_orders_user_status ON dbo.store_orders(userid, status, created_at DESC);
COMMIT;
GO
