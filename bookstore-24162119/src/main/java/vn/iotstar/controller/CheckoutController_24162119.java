package vn.iotstar.controller;

import java.io.IOException;
import java.util.UUID;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.*;
import vn.iotstar.model.*;
import vn.iotstar.service.CommerceService_24162119;

@WebServlet("/checkout")
public class CheckoutController_24162119 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CommerceService_24162119 service = new CommerceService_24162119();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User_24162119 user = (User_24162119) session.getAttribute("currentUser");
        CartView_24162119 cart = service.getCart(user.getId());
        if (!cart.isCheckoutAllowed()) {
            session.setAttribute("errorMessage", "Giỏ hàng trống hoặc có sách không đủ tồn kho. Vui lòng kiểm tra giỏ hàng.");
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }
        synchronized (session) {
            if (session.getAttribute("checkoutToken") == null) session.setAttribute("checkoutToken", UUID.randomUUID().toString());
        }
        request.setAttribute("cart", cart);
        // The existing user.phone is an Integer and cannot preserve a leading zero.
        request.setAttribute("form", new CheckoutForm_24162119(user.getFullname(), "", "", ""));
        request.getRequestDispatcher("/WEB-INF/views/user/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User_24162119 user = (User_24162119) session.getAttribute("currentUser");
        CheckoutForm_24162119 form = new CheckoutForm_24162119(request.getParameter("recipientName"), request.getParameter("phone"), request.getParameter("address"), request.getParameter("note"));
        String token = request.getParameter("checkoutToken");
        if (token == null || !token.equals(session.getAttribute("checkoutToken"))) {
            response.sendError(400, "Phiên thanh toán đã thay đổi. Vui lòng mở lại trang thanh toán.");
            return;
        }
        try {
            if (!"COD".equals(request.getParameter("paymentMethod"))) throw new IllegalArgumentException("Chỉ hỗ trợ thanh toán khi nhận hàng (COD).");
            Order_24162119 order = service.checkout(user.getId(), form, token);
            session.setAttribute("successMessage", "Đặt hàng COD thành công. Bạn thanh toán khi nhận hàng.");
            response.sendRedirect(request.getContextPath() + "/order-detail?id=" + order.getId());
            return;
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
        } catch (RuntimeException e) {
            getServletContext().log("Unable to place COD order", e);
            request.setAttribute("errorMessage", "Không đặt được hàng. Vui lòng thử lại; kiểm tra lịch sử đơn hàng trước khi đặt lại.");
        }
        request.setAttribute("form", form);
        request.setAttribute("cart", service.getCart(user.getId()));
        request.getRequestDispatcher("/WEB-INF/views/user/checkout.jsp").forward(request, response);
    }
}
