<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<html>

<head>

    <title>Author Form</title>

</head>

<body>

<div class="container py-5">

    <div class="card shadow-sm">

        <div class="card-body">

            <h2 class="mb-4">

                ${empty author.authorId
                    ? 'Thêm Author'
                    : 'Cập nhật Author'}

            </h2>


            <form method="post"
                  action="${pageContext.request.contextPath}/admin/authors/save">

                <input type="hidden"
                       name="id"
                       value="${author.authorId}">


                <div class="mb-3">

                    <label class="form-label">
                        Author name
                    </label>

                    <input type="text"
                           name="authorName"
                           value="${author.authorName}"
                           class="form-control"
                           required>

                </div>


                <div class="mb-3">

                    <label class="form-label">
                        Date of birth
                    </label>

                    <input type="date"
                           name="dateOfBirth"
                           value="${author.dateOfBirth}"
                           class="form-control">

                </div>


                <button type="submit"
                        class="btn btn-primary">
                    Lưu
                </button>

                <a class="btn btn-secondary"
                   href="${pageContext.request.contextPath}/admin/authors">
                    Hủy
                </a>

            </form>

        </div>

    </div>

</div>

</body>

</html>