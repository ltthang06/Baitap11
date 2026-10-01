-- Chỉ chạy file này khi CHƯA có database bài kiểm tra quá trình.
-- Nếu đã có dữ liệu cũ: bỏ qua file này, chạy 02_cart_orders.sql.
USE master;
GO
IF DB_ID(N'BookStore_24162119') IS NULL
    CREATE DATABASE BookStore_24162119;
GO
USE BookStore_24162119;
GO
IF OBJECT_ID(N'dbo.users', N'U') IS NULL
CREATE TABLE dbo.users (
    id INT IDENTITY(1,1) PRIMARY KEY,
    email VARCHAR(50) NOT NULL UNIQUE,
    fullname NVARCHAR(50), phone INT,
    passwd VARCHAR(32) NOT NULL,
    signup_date DATETIME2, last_login DATETIME2,
    is_admin BIT NOT NULL DEFAULT 0
);
IF OBJECT_ID(N'dbo.author', N'U') IS NULL
CREATE TABLE dbo.author (
    author_id INT IDENTITY(1,1) PRIMARY KEY,
    author_name NVARCHAR(100), date_of_birth DATE
);
IF OBJECT_ID(N'dbo.books', N'U') IS NULL
CREATE TABLE dbo.books (
    bookid INT IDENTITY(1,1) PRIMARY KEY,
    isbn INT, title NVARCHAR(255), publisher NVARCHAR(255),
    price DECIMAL(18,2), description NVARCHAR(MAX),
    publish_date DATE, cover_image NVARCHAR(MAX), quantity INT
);
IF OBJECT_ID(N'dbo.book_author', N'U') IS NULL
CREATE TABLE dbo.book_author (
    bookid INT NOT NULL REFERENCES dbo.books(bookid),
    author_id INT NOT NULL REFERENCES dbo.author(author_id),
    PRIMARY KEY(bookid, author_id)
);
IF OBJECT_ID(N'dbo.rating', N'U') IS NULL
CREATE TABLE dbo.rating (
    userid INT NOT NULL REFERENCES dbo.users(id),
    bookid INT NOT NULL REFERENCES dbo.books(bookid),
    rating TINYINT, review_text NVARCHAR(MAX),
    PRIMARY KEY(userid, bookid)
);
GO
