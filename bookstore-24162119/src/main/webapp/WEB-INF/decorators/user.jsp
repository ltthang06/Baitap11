<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="vi">

<head>
    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1">

    <title>
        <sitemesh:write property="title"/>
    </title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <sitemesh:write property="head"/>
</head>

<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">

        <a class="navbar-brand"
           href="${pageContext.request.contextPath}/home">
            BookStore
        </a>

        <div class="navbar-nav">

            <a class="nav-link"
               href="${pageContext.request.contextPath}/home">
                Trang Chủ
            </a>

            <a class="nav-link"
               href="${pageContext.request.contextPath}/home">
                Sản phẩm
            </a>

            <a class="nav-link" href="${pageContext.request.contextPath}/cart">Giỏ hàng</a>
            <a class="nav-link" href="${pageContext.request.contextPath}/orders">Lịch sử đặt hàng</a>

            <c:choose>

                <c:when test="${not empty sessionScope.currentUser}">
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/logout">
                        Đăng xuất
                    </a>
                </c:when>

                <c:otherwise>
                    <a class="nav-link"
                       href="${pageContext.request.contextPath}/login">
                        Đăng nhập
                    </a>
                </c:otherwise>

            </c:choose>

            <c:if test="${sessionScope.currentUser.isAdmin == true}">
                <a class="nav-link"
                   href="${pageContext.request.contextPath}/admin/books">
                    Trang quản trị
                </a>
            </c:if>

        </div>
    </div>
</nav>


<main class="container py-4">
    <sitemesh:write property="body"/>
</main>


<footer class="bg-dark text-white text-center py-3 mt-5">

    Họ tên: Lê Thành Thắng
    |
    MSSV: 24162119
    |
    Mã đề: 01

</footer>

</body>
</html>
