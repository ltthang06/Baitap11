package vn.iotstar.controller;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import vn.iotstar.entity.User_24162119;
import vn.iotstar.service.CommerceService_24162119;

@WebServlet(urlPatterns = {"/cart", "/cart/add", "/cart/update", "/cart/remove", "/cart/clear"})
public class CartController_24162119 extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CommerceService_24162119 service = new CommerceService_24162119();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!"/cart".equals(request.getServletPath())) { response.sendError(405); return; }
        User_24162119 user = (User_24162119) request.getSession().getAttribute("currentUser");
        request.setAttribute("cart", service.getCart(user.getId()));
        for (String name : new String[]{"successMessage", "errorMessage"}) {
            request.setAttribute(name, request.getSession().getAttribute(name));
            request.getSession().removeAttribute(name);
        }
        request.getRequestDispatcher("/WEB-INF/views/user/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Integer userId = ((User_24162119) session.getAttribute("currentUser")).getId();
        try {
            switch (request.getServletPath()) {
                case "/cart/add": service.add(userId, number(request, "bookId"), number(request, "quantity")); break;
                case "/cart/update": service.update(userId, number(request, "bookId"), number(request, "quantity")); break;
                case "/cart/remove": service.remove(userId, number(request, "bookId")); break;
                case "/cart/clear": service.clear(userId); break;
                default: response.sendError(405); return;
            }
            session.removeAttribute("checkoutToken");
            session.setAttribute("successMessage", "Đã cập nhật giỏ hàng.");
        } catch (IllegalArgumentException e) {
            session.setAttribute("errorMessage", e.getMessage());
        } catch (RuntimeException e) {
            getServletContext().log("Unable to update cart", e);
            session.setAttribute("errorMessage", "Không cập nhật được giỏ hàng. Vui lòng thử lại.");
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }

    private int number(HttpServletRequest request, String name) {
        try { return Integer.parseInt(request.getParameter(name)); }
        catch (NumberFormatException e) { throw new IllegalArgumentException("Mã sách và số lượng phải là số nguyên hợp lệ."); }
    }
}
