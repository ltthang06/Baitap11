<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN"/>

<html>

<head>

    <title>Trang Chủ</title>

</head>

<body>

<div class="container py-5">

    <h2 class="mb-4">
        Danh sách sách
    </h2>

    <div class="row g-4">

        <c:forEach items="${books.content}"
                   var="book">

            <div class="col-md-4">

                <div class="card h-100 shadow-sm">

                    <img src="${book.coverImage}"
                         class="card-img-top"
                         style="height:260px;
                                object-fit:cover;"
                         alt="${book.title}">

                    <div class="card-body">

                        <h5>

                            <a href="${pageContext.request.contextPath}/book-detail?id=${book.bookid}">
                                ${book.title}
                            </a>

                        </h5>

                        <p>
                            <strong>Mã isbn:</strong>
                            ${book.isbn}
                        </p>

                        <p>

                            <strong>Tác giả:</strong>

                            <c:forEach items="${book.authors}"
                                       var="author"
                                       varStatus="loop">

                                ${author.authorName}

                                <c:if test="${!loop.last}">
                                    ,
                                </c:if>

                            </c:forEach>

                        </p>

                        <p>
                            <strong>Publisher:</strong>
                            ${book.publisher}
                        </p>

                        <p>
                            <strong>Publisher_date:</strong>
                            ${book.publishDate}
                        </p>

                        <p>
                            <strong>Quantity:</strong>
                            ${book.quantity}
                        </p>

                        <p>
                            <strong>Review:</strong>
                            (${reviewCounts[book.bookid]})
                        </p>

                        <p><strong>Giá:</strong> <fmt:formatNumber value="${book.price}" type="number"/> đ</p>
                        <c:choose><c:when test="${book.quantity > 0}">
                            <form method="post" action="${pageContext.request.contextPath}/cart/add">
                                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                <input type="hidden" name="bookId" value="${book.bookid}">
                                <input type="hidden" name="quantity" value="1">
                                <button class="btn btn-success" type="submit">Thêm vào giỏ hàng</button>
                            </form>
                        </c:when><c:otherwise><span class="badge bg-secondary">Hết hàng</span></c:otherwise></c:choose>
                    </div>

                </div>

            </div>

        </c:forEach>

    </div>


    <c:if test="${books.totalPages > 0}">

        <div class="d-flex justify-content-center mt-4">

            <div class="btn-group">

                <c:forEach begin="0"
                           end="${books.totalPages - 1}"
                           var="i">

                    <a
                       href="${pageContext.request.contextPath}/home?page=${i}"
                       class="btn ${i == books.page
                            ? 'btn-primary'
                            : 'btn-outline-primary'}">

                        ${i + 1}

                    </a>

                </c:forEach>

            </div>

        </div>

    </c:if>

</div>

</body>

</html>
