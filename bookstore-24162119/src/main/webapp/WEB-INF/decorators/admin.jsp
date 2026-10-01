<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html lang="vi">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width,
                   initial-scale=1">

    <title>
        Admin -
        <sitemesh:write property="title"/>
    </title>

    <link
        href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
        rel="stylesheet">

    <sitemesh:write property="head"/>

</head>

<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">

    <div class="container">

        <a class="navbar-brand"
           href="${pageContext.request.contextPath}/admin/books">
            Admin BookStore
        </a>

        <div class="navbar-nav">

            <a class="nav-link"
               href="${pageContext.request.contextPath}/home">
                Trang Chủ
            </a>

            <a class="nav-link"
               href="${pageContext.request.contextPath}/products">
                Sản phẩm
            </a>

            <a class="nav-link"
               href="${pageContext.request.contextPath}/admin/books">
                Books
            </a>

            <a class="nav-link"
               href="${pageContext.request.contextPath}/admin/authors">
                Authors
            </a>

            <a class="nav-link"
               href="${pageContext.request.contextPath}/logout">
                Đăng xuất
            </a>

        </div>

    </div>

</nav>


<sitemesh:write property="body"/>


<footer class="bg-dark text-white text-center py-3 mt-5">

    Họ tên: Lê Thành Thắng
    |
    MSSV: 24162119
    |
    Mã đề: 01

</footer>

</body>

</html>