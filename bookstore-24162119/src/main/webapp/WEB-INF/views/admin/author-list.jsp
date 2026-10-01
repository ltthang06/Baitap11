<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="jakarta.tags.core" %>

<html>

<head>

    <title>Quản lý Authors</title>

</head>

<body>

<div class="container py-5">

    <div class="d-flex justify-content-between mb-4">

        <h2>
            Quản lý Authors
        </h2>

        <a class="btn btn-primary"
           href="${pageContext.request.contextPath}/admin/authors/form">

            + Thêm Author

        </a>

    </div>


    <table class="table table-bordered bg-white">

        <thead>

        <tr>

            <th>ID</th>
            <th>Author name</th>
            <th>Date of birth</th>
            <th>Thao tác</th>

        </tr>

        </thead>

        <tbody>

        <c:forEach items="${authors.content}"
                   var="author">

            <tr>

                <td>
                    ${author.authorId}
                </td>

                <td>
                    ${author.authorName}
                </td>

                <td>
                    ${author.dateOfBirth}
                </td>

                <td>

                    <a class="btn btn-sm btn-outline-primary"
                       href="${pageContext.request.contextPath}/admin/authors/form?id=${author.authorId}">
                        Sửa
                    </a>

                    <form method="post"
                          class="d-inline"
                          action="${pageContext.request.contextPath}/admin/authors/delete"
                          onsubmit="return confirm('Xóa tác giả này?');">

                        <input type="hidden"
                               name="id"
                               value="${author.authorId}">

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


    <c:if test="${authors.totalPages > 0}">

        <div class="btn-group">

            <c:forEach begin="0"
                       end="${authors.totalPages - 1}"
                       var="i">

                <a
                  href="${pageContext.request.contextPath}/admin/authors?page=${i}"
                  class="btn ${i == authors.page
                    ? 'btn-primary'
                    : 'btn-outline-primary'}">

                    ${i + 1}

                </a>

            </c:forEach>

        </div>

    </c:if>

</div>

</body>

</html>