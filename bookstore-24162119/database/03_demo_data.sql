-- Tùy chọn: tài khoản và sách mẫu để kiểm tra ngay, không cần gửi OTP.
-- Mật khẩu mẫu: 123456 (theo cơ chế MD5 của project gốc).
IF NOT EXISTS (SELECT 1 FROM dbo.users WHERE email = 'user24162119@example.com')
    INSERT dbo.users(email,fullname,phone,passwd,signup_date,is_admin)
    VALUES('user24162119@example.com',N'User kiểm thử',NULL,'e10adc3949ba59abbe56e057f20f883e',SYSDATETIME(),0);
IF NOT EXISTS (SELECT 1 FROM dbo.users WHERE email = 'admin24162119@example.com')
    INSERT dbo.users(email,fullname,phone,passwd,signup_date,is_admin)
    VALUES('admin24162119@example.com',N'Admin kiểm thử',NULL,'e10adc3949ba59abbe56e057f20f883e',SYSDATETIME(),1);
IF NOT EXISTS (SELECT 1 FROM dbo.books)
BEGIN
    INSERT dbo.author(author_name,date_of_birth) VALUES(N'Tác giả mẫu','1990-01-01');
    DECLARE @authorId INT = SCOPE_IDENTITY();
    INSERT dbo.books(isbn,title,publisher,price,description,publish_date,cover_image,quantity)
    VALUES(241621191,N'Lập trình Java',N'Nhà xuất bản Giáo dục',125000,N'Sách học lập trình Java.','2026-01-01','https://placehold.co/300x400?text=Java',120),
          (241621192,N'Cơ sở dữ liệu',N'Nhà xuất bản Giáo dục',95000,N'Sách học cơ sở dữ liệu.','2026-01-01','https://placehold.co/300x400?text=Database',5),
          (241621193,N'Lập trình Web',N'Nhà xuất bản Giáo dục',150000,N'Sách học Servlet và JSP.','2026-01-01','https://placehold.co/300x400?text=Web',0);
    INSERT dbo.book_author(bookid,author_id) SELECT bookid,@authorId FROM dbo.books;
END;
GO
