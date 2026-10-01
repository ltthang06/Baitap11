<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%@ taglib prefix="c"
           uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN"/>

<html>

<head>

    <title>${book.title}</title>

</head>

<body>

<div class="container py-5">

    <div class="card shadow-sm">

        <div class="card-body">

            <div class="row">

                <div class="col-md-4">

                    <img src="${book.coverImage}"
                         class="img-fluid rounded"
                         alt="${book.title}">

                </div>

                <div class="col-md-8">

                    <h2>
                        ${book.title}
                    </h2>

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
                        <strong>Reviews:</strong>
                        (${reviewCount})
                    </p>

                    <p><strong>Giá:</strong> <fmt:formatNumber value="${book.price}" type="number"/> đ</p>
                    <c:choose><c:when test="${book.quantity > 0}">
                        <form class="d-flex gap-2 align-items-center" method="post" action="${pageContext.request.contextPath}/cart/add">
                            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                            <input type="hidden" name="bookId" value="${book.bookid}">
                            <label for="quantity">Số lượng</label>
                            <input id="quantity" class="form-control" style="width:90px" type="number" name="quantity" value="1" min="1" max="${book.quantity > 99 ? 99 : book.quantity}" required>
                            <button class="btn btn-success" type="submit">Thêm vào giỏ hàng</button>
                        </form>
                    </c:when><c:otherwise><span class="badge bg-secondary">Hết hàng</span></c:otherwise></c:choose>
                </div>

            </div>


            <hr>


            <h4>Reviews</h4>

            <c:forEach items="${reviews}"
                       var="review">

                <div class="border rounded p-3 mb-2">

                    <strong>
                        ${review.user.fullname}
                    </strong>

                    -
                    ${review.rating}/5

                    <div>
                        ${review.reviewText}
                    </div>

                </div>

            </c:forEach>


            <hr>


            <h4>Form thêm reviews</h4>

            <c:choose>

                <c:when test="${not empty sessionScope.currentUser}">

                    <form method="post"
                          action="${pageContext.request.contextPath}/review/add">

                        <input type="hidden"
                               name="bookId"
                               value="${book.bookid}">


                        <div class="mb-3">

                            <label class="form-label">
                                Rating
                            </label>

                            <select name="rating"
                                    class="form-select">

                                <option value="5">5</option>
                                <option value="4">4</option>
                                <option value="3">3</option>
                                <option value="2">2</option>
                                <option value="1">1</option>

                            </select>

                        </div>


                        <div class="mb-3">

                            <label class="form-label">
                                Review
                            </label>

                            <textarea name="reviewText"
                                      class="form-control"
                                      required></textarea>

                        </div>


                        <button type="submit"
                                class="btn btn-primary">

                            Submit

                        </button>

                    </form>

                </c:when>

                <c:otherwise>

                    <a href="${pageContext.request.contextPath}/login"
                       class="btn btn-primary">

                        Đăng nhập để review

                    </a>

                </c:otherwise>

            </c:choose>

        </div>

    </div>

</div>

</body>

</html>
