package vn.iotstar.filter;

import java.io.IOException;
import java.util.UUID;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class ShopFilter_24162119 implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();
        synchronized (session) {
            if (session.getAttribute("csrfToken") == null) session.setAttribute("csrfToken", UUID.randomUUID().toString());
        }
        String path = request.getServletPath();
        boolean shop = path.equals("/cart") || path.startsWith("/cart/") || path.equals("/checkout")
                || path.equals("/orders") || path.equals("/order-detail");
        if (shop) {
            response.setHeader("Cache-Control", "no-store");
            if (session.getAttribute("currentUser") == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return;
            }
            if ("POST".equals(request.getMethod()) && !session.getAttribute("csrfToken").equals(request.getParameter("csrfToken"))) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Phiên làm việc không hợp lệ. Vui lòng tải lại trang.");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
