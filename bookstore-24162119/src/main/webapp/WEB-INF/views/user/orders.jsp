<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<fmt:setLocale value="vi_VN"/>
<html><head><title>Lịch sử đặt hàng</title></head><body>
<h2>Lịch sử đặt hàng</h2>
<form class="d-flex gap-2 align-items-center my-3" method="get" action="${pageContext.request.contextPath}/orders">
    <label for="status">Trạng thái</label>
    <select class="form-select" style="max-width:300px" name="status" id="status">
        <option value="">Tất cả đơn hàng</option>
        <c:forEach items="${statuses}" var="status"><option value="${status.code}" ${selectedStatus == status.code ? 'selected' : ''}><c:out value="${status.label}"/></option></c:forEach>
    </select>
    <button type="submit" class="btn btn-primary">Lọc</button>
</form>
<c:choose><c:when test="${empty orders}"><div class="alert alert-info">Không có đơn hàng ở trạng thái đã chọn.</div>
    <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/home">Tiếp tục mua sách</a></c:when>
    <c:otherwise><div class="table-responsive"><table class="table table-bordered align-middle bg-white">
        <thead><tr><th>Mã đơn</th><th>Ngày đặt</th><th>Người nhận</th><th>Tổng tiền</th><th>Thanh toán</th><th>Trạng thái</th><th>Chi tiết</th></tr></thead>
        <tbody><c:forEach items="${orders}" var="order"><tr>
            <td>#${order.id}</td><td>${order.createdAtText}</td><td><c:out value="${order.recipientName}"/></td>
            <td><fmt:formatNumber value="${order.total}" type="number"/> đ</td><td>COD</td>
            <td><span class="badge bg-primary"><c:out value="${order.status.label}"/></span></td>
            <td><a class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/order-detail?id=${order.id}">Xem</a></td>
        </tr></c:forEach></tbody>
    </table></div></c:otherwise>
</c:choose>
</body></html>
