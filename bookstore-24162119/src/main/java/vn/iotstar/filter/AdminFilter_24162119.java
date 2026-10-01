package vn.iotstar.filter;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import vn.iotstar.entity.User_24162119;

public class
        AdminFilter_24162119
        implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException,
                   ServletException {

        HttpServletRequest req =
                (HttpServletRequest)
                        request;

        HttpServletResponse res =
                (HttpServletResponse)
                        response;

        HttpSession session =
                req.getSession(false);

        User_24162119 user =
                session == null
                        ? null
                        :
                        (User_24162119)
                        session.getAttribute(
                                "currentUser"
                        );

        if (user == null
                ||
                !Boolean.TRUE.equals(
                        user.getIsAdmin()
                )) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/login"
            );

            return;
        }

        chain.doFilter(
                request,
                response
        );
    }
}