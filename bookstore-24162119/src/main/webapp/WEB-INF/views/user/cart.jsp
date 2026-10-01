<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN"/>
<html><head><title>Giỏ hàng</title></head><body>
<h2>Giỏ hàng của bạn</h2>
<c:if test="${not empty successMessage}"><div class="alert alert-success" role="status"><c:out value="${successMessage}"/></div></c:if>
<c:if test="${not empty errorMessage}"><div class="alert alert-danger" role="alert"><c:out value="${errorMessage}"/></div></c:if>
<c:choose>
    <c:when test="${empty cart.items}">
        <div class="alert alert-info">Giỏ hàng đang trống.</div>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/home">Chọn sách</a>
    </c:when>
    <c:otherwise>
        <p>Mỗi sách được mua từ 1 đến 99 cuốn và không vượt quá số lượng còn trong kho.</p>
        <div class="table-responsive"><table class="table table-bordered align-middle bg-white">
            <thead><tr><th>Sách</th><th>Đơn giá</th><th>Số lượng</th><th>Thành tiền</th><th>Thao tác</th></tr></thead>
            <tbody><c:forEach items="${cart.items}" var="item">
                <tr>
                    <td><a href="${pageContext.request.contextPath}/book-detail?id=${item.book.bookid}"><c:out value="${item.book.title}"/></a>
                        <div class="text-muted small">Còn ${item.book.quantity} cuốn</div>
                        <c:if test="${!item.available}"><div class="text-danger">Không đủ tồn kho hoặc giá không hợp lệ. Hãy sửa số lượng hoặc xóa sách.</div></c:if>
                    </td>
                    <td><fmt:formatNumber value="${item.book.price}" type="number"/> đ</td>
                    <td>
                        <c:choose><c:when test="${item.maxQuantity > 0}">
                            <form class="d-flex gap-2" method="post" action="${pageContext.request.contextPath}/cart/update">
                                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                                <input type="hidden" name="bookId" value="${item.book.bookid}">
                                <input class="form-control" style="width:90px" aria-label="Số lượng sách" type="number" name="quantity" min="1" max="${item.maxQuantity}" value="${item.quantity}" required>
                                <button class="btn btn-outline-primary" type="submit">Cập nhật</button>
                            </form>
                        </c:when><c:otherwise>${item.quantity} cuốn — Đã hết hàng</c:otherwise></c:choose>
                    </td>
                    <td><fmt:formatNumber value="${item.subtotal}" type="number"/> đ</td>
                    <td><form method="post" action="${pageContext.request.contextPath}/cart/remove">
                        <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                        <input type="hidden" name="bookId" value="${item.book.bookid}">
                        <button class="btn btn-outline-danger" type="submit">Xóa</button>
                    </form></td>
                </tr>
            </c:forEach></tbody>
        </table></div>
        <div class="card p-3 mb-3"><strong>Tổng ${cart.itemCount} cuốn: <fmt:formatNumber value="${cart.total}" type="number"/> đ</strong></div>
        <div class="d-flex gap-2 flex-wrap">
            <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/home">Tiếp tục mua sách</a>
            <form method="post" action="${pageContext.request.contextPath}/cart/clear">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                <button class="btn btn-outline-danger" type="submit">Xóa toàn bộ giỏ hàng</button>
            </form>
            <c:if test="${cart.checkoutAllowed}"><a class="btn btn-success" href="${pageContext.request.contextPath}/checkout">Thanh toán COD</a></c:if>
        </div>
    </c:otherwise>
</c:choose>
</body></html>
