package vn.iotstar.controller;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.*;
import vn.iotstar.service.CommerceService_24162119;

@WebServlet(urlPatterns = {"/orders", "/order-detail"})
public class OrderController_24162119 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CommerceService_24162119 service = new CommerceService_24162119();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer userId = ((User_24162119) request.getSession().getAttribute("currentUser")).getId();
        if ("/order-detail".equals(request.getServletPath())) {
            long id;
            try { id = Long.parseLong(request.getParameter("id")); }
            catch (NumberFormatException e) { response.sendError(400, "Mã đơn hàng không hợp lệ."); return; }
            Order_24162119 order = service.findOrder(userId, id);
            if (order == null) { response.sendError(404, "Không tìm thấy đơn hàng."); return; }
            request.setAttribute("order", order);
            request.setAttribute("successMessage", request.getSession().getAttribute("successMessage"));
            request.getSession().removeAttribute("successMessage");
            request.getRequestDispatcher("/WEB-INF/views/user/order-detail.jsp").forward(request, response);
            return;
        }
        String code = request.getParameter("status");
        OrderStatus_24162119 status = null;
        if (code != null && !code.isBlank()) {
            try { status = OrderStatus_24162119.valueOf(code); }
            catch (IllegalArgumentException e) { response.sendError(400, "Trạng thái đơn hàng không hợp lệ."); return; }
        }
        request.setAttribute("statuses", OrderStatus_24162119.values());
        request.setAttribute("selectedStatus", status == null ? "" : status.name());
        request.setAttribute("orders", service.findOrders(userId, status));
        request.getRequestDispatcher("/WEB-INF/views/user/orders.jsp").forward(request, response);
    }
}
