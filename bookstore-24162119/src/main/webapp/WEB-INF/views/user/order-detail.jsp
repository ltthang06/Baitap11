<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN"/>
<html><head><title>Chi tiết đơn hàng #${order.id}</title></head><body>
<h2>Đơn hàng #${order.id}</h2>
<c:if test="${not empty successMessage}"><div class="alert alert-success" role="status"><c:out value="${successMessage}"/></div></c:if>
<div class="card p-4 mb-4">
    <p><strong>Ngày đặt:</strong> ${order.createdAtText}</p>
    <p><strong>Trạng thái:</strong> <span class="badge bg-primary"><c:out value="${order.status.label}"/></span></p>
    <p><strong>Người nhận:</strong> <c:out value="${order.recipientName}"/></p>
    <p><strong>Số điện thoại:</strong> <c:out value="${order.phone}"/></p>
    <p><strong>Địa chỉ:</strong> <c:out value="${order.address}"/></p>
    <c:if test="${not empty order.note}"><p><strong>Ghi chú:</strong> <c:out value="${order.note}"/></p></c:if>
    <p class="mb-0"><strong>Thanh toán:</strong> COD — Thanh toán khi nhận hàng</p>
</div>
<div class="table-responsive"><table class="table table-bordered bg-white">
    <thead><tr><th>Sách</th><th>Đơn giá lúc đặt</th><th>Số lượng</th><th>Thành tiền</th></tr></thead>
    <tbody><c:forEach items="${order.items}" var="item"><tr>
        <td><c:out value="${item.bookTitle}"/></td><td><fmt:formatNumber value="${item.unitPrice}" type="number"/> đ</td>
        <td>${item.quantity}</td><td><fmt:formatNumber value="${item.subtotal}" type="number"/> đ</td>
    </tr></c:forEach></tbody>
</table></div>
<p>Phí giao hàng: 0 đ</p>
<h4>Tổng tiền: <fmt:formatNumber value="${order.total}" type="number"/> đ</h4>
<a class="btn btn-primary mt-3" href="${pageContext.request.contextPath}/orders">Lịch sử đặt hàng</a>
<a class="btn btn-outline-secondary mt-3" href="${pageContext.request.contextPath}/home">Tiếp tục mua sách</a>
</body></html>
