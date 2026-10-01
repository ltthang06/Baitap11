<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="jakarta.tags.core" %>

<html>

<head>

    <title>Quản lý Books</title>

</head>

<body>

<div class="container py-5">

    <div class="d-flex justify-content-between mb-4">

        <h2>
            Quản lý Books
        </h2>

        <a class="btn btn-primary"
           href="${pageContext.request.contextPath}/admin/books/form">

            + Thêm Book

        </a>

    </div>


    <div class="card shadow-sm">

        <div class="table-responsive">

            <table class="table table-hover mb-0">

                <thead>

                <tr>

                    <th>ID</th>
                    <th>ISBN</th>
                    <th>Title</th>
                    <th>Publisher</th>
                    <th>Quantity</th>
                    <th>Thao tác</th>

                </tr>

                </thead>

                <tbody>

                <c:forEach items="${books.content}"
                           var="book">

                    <tr>

                        <td>
                            ${book.bookid}
                        </td>

                        <td>
                            ${book.isbn}
                        </td>

                        <td>
                            ${book.title}
                        </td>

                        <td>
                            ${book.publisher}
                        </td>

                        <td>
                            ${book.quantity}
                        </td>

                        <td>

                            <a class="btn btn-sm btn-outline-secondary"
                               href="${pageContext.request.contextPath}/book-detail?id=${book.bookid}">
                                Xem
                            </a>

                            <a class="btn btn-sm btn-outline-primary"
                               href="${pageContext.request.contextPath}/admin/books/form?id=${book.bookid}">
                                Sửa
                            </a>

                            <form method="post"
                                  class="d-inline"
                                  action="${pageContext.request.contextPath}/admin/books/delete"
                                  onsubmit="return confirm('Xóa sách này?');">

                                <input type="hidden"
                                       name="id"
                                       value="${book.bookid}">

                                <button type="submit"
                                        class="btn btn-sm btn-outline-danger">
                                    Xóa
                                </button>

                            </form>

                        </td>

                    </tr>

                </c:forEach>

                </tbody>

            </table>

        </div>

    </div>


    <c:if test="${books.totalPages > 0}">

        <div class="d-flex justify-content-center mt-4">

            <div class="btn-group">

                <c:forEach begin="0"
                           end="${books.totalPages - 1}"
                           var="i">

                    <a
                      href="${pageContext.request.contextPath}/admin/books?page=${i}"
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