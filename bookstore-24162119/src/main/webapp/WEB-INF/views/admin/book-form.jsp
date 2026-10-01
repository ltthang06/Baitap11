<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="jakarta.tags.core" %>

<html>

<head>

    <title>Book Form</title>

</head>

<body>

<div class="container py-5">

    <div class="card shadow-sm">

        <div class="card-body">

            <h2 class="mb-4">

                ${empty book.bookid
                    ? 'Thêm Book'
                    : 'Cập nhật Book'}

            </h2>


            <form method="post"
                  action="${pageContext.request.contextPath}/admin/books/save">

                <input type="hidden"
                       name="id"
                       value="${book.bookid}">


                <div class="mb-3">

                    <label class="form-label">
                        ISBN
                    </label>

                    <input type="number"
                           name="isbn"
                           value="${book.isbn}"
                           class="form-control"
                           required>

                </div>


                <div class="mb-3">

                    <label class="form-label">
                        Title
                    </label>

                    <input type="text"
                           name="title"
                           value="${book.title}"
                           class="form-control"
                           required>

                </div>


                <div class="mb-3">

                    <label class="form-label">
                        Publisher
                    </label>

                    <input type="text"
                           name="publisher"
                           value="${book.publisher}"
                           class="form-control">

                </div>


                <div class="mb-3">

                    <label class="form-label">
                        Price
                    </label>

                    <input type="number"
                           step="0.01"
                           name="price"
                           value="${book.price}"
                           class="form-control"
                           required>

                </div>


                <div class="mb-3">

                    <label class="form-label">
                        Description
                    </label>

                    <textarea name="description"
                              class="form-control">${book.description}</textarea>

                </div>


                <div class="mb-3">

                    <label class="form-label">
                        Publish Date
                    </label>

                    <input type="date"
                           name="publishDate"
                           value="${book.publishDate}"
                           class="form-control"
                           required>

                </div>


                <div class="mb-3">

                    <label class="form-label">
                        Cover Image URL
                    </label>

                    <input type="text"
                           name="coverImage"
                           value="${book.coverImage}"
                           class="form-control">

                </div>


                <div class="mb-3">

                    <label class="form-label">
                        Quantity
                    </label>

                    <input type="number"
                           name="quantity"
                           value="${book.quantity}"
                           class="form-control"
                           required>

                </div>


                <div class="mb-3">

                    <label class="form-label">
                        Authors
                    </label>

                    <select name="authorIds"
                            class="form-select"
                            multiple
                            required>

                        <c:forEach items="${authors}"
                                   var="author">

                            <option
                              value="${author.authorId}"
                              ${selectedAuthorIds.contains(author.authorId)
                                ? 'selected'
                                : ''}>

                                ${author.authorName}

                            </option>

                        </c:forEach>

                    </select>

                    <small class="text-muted">
                        Giữ Ctrl để chọn nhiều tác giả.
                    </small>

                </div>


                <button type="submit"
                        class="btn btn-primary">
                    Lưu
                </button>

                <a class="btn btn-secondary"
                   href="${pageContext.request.contextPath}/admin/books">
                    Hủy
                </a>

            </form>

        </div>

    </div>

</div>

</body>

</html>