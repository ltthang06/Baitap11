<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN"/>
<html><head><title>Thanh toán COD</title></head><body>
<h2>Thanh toán khi nhận hàng (COD)</h2>
<c:if test="${not empty errorMessage}"><div class="alert alert-danger" role="alert"><c:out value="${errorMessage}"/></div></c:if>
<div class="row g-4">
    <div class="col-lg-7"><div class="card p-4">
        <form method="post" action="${pageContext.request.contextPath}/checkout">
            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
            <input type="hidden" name="checkoutToken" value="${sessionScope.checkoutToken}">
            <div class="mb-3"><label for="recipientName" class="form-label">Họ tên người nhận</label>
                <input id="recipientName" name="recipientName" class="form-control" autocomplete="name" minlength="2" maxlength="100" value="<c:out value='${form.recipientName}'/>" required></div>
            <div class="mb-3"><label for="phone" class="form-label">Số điện thoại</label>
                <input id="phone" name="phone" type="tel" class="form-control" autocomplete="tel" pattern="(0[0-9]{9}|\+84[0-9]{9})" maxlength="20" placeholder="0901234567" value="<c:out value='${form.phone}'/>" required>
                <div class="form-text">10 số bắt đầu bằng 0, hoặc +84 và 9 số.</div></div>
            <div class="mb-3"><label for="address" class="form-label">Địa chỉ giao hàng</label>
                <textarea id="address" name="address" class="form-control" autocomplete="street-address" minlength="10" maxlength="500" rows="3" required><c:out value="${form.address}"/></textarea></div>
            <div class="mb-3"><label for="note" class="form-label">Ghi chú (tùy chọn)</label>
                <textarea id="note" name="note" class="form-control" maxlength="500" rows="2"><c:out value="${form.note}"/></textarea></div>
            <div class="form-check mb-3"><input class="form-check-input" type="radio" name="paymentMethod" id="cod" value="COD" checked required>
                <label class="form-check-label" for="cod">COD — Thanh toán tiền mặt khi nhận hàng</label></div>
            <p class="text-muted">Không thu tiền trực tuyến. Tổng tiền được kiểm tra lại theo giá và tồn kho tại thời điểm đặt hàng.</p>
            <c:choose><c:when test="${cart.checkoutAllowed}"><button class="btn btn-success" type="submit">Xác nhận đặt hàng COD</button></c:when>
                <c:otherwise><div class="alert alert-warning">Giỏ hàng cần được cập nhật trước khi đặt hàng.</div></c:otherwise></c:choose>
            <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/cart">Về giỏ hàng</a>
        </form>
    </div></div>
    <div class="col-lg-5"><div class="card p-4"><h4>Đơn hàng của bạn</h4>
        <c:forEach items="${cart.items}" var="item"><div class="border-bottom py-2">
            <c:out value="${item.book.title}"/> × ${item.quantity}
            <div><fmt:formatNumber value="${item.subtotal}" type="number"/> đ</div>
        </div></c:forEach>
        <p class="mt-3 mb-1">Phí giao hàng: 0 đ</p>
        <strong>Tổng tiền: <fmt:formatNumber value="${cart.total}" type="number"/> đ</strong>
    </div></div>
</div>
</body></html>
