package vn.iotstar.controller;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.iotstar.entity.User_24162119;
import vn.iotstar.service.UserService_24162119;
import vn.iotstar.service.impl.UserServiceImpl_24162119;
import vn.iotstar.util.MailUtil_24162119;

@WebServlet(
    urlPatterns = {
        "/login",
        "/logout",
        "/register",
        "/verify-otp"
    }
)
public class
        AuthController_24162119
        extends HttpServlet {

    private static final long
            serialVersionUID = 1L;

    private UserService_24162119
            userService;

    @Override
    public void init() {

        userService =
                new UserServiceImpl_24162119();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
                   IOException {

        String path =
                request.getServletPath();

        if ("/logout".equals(path)) {

            HttpSession session =
                    request
                            .getSession(false);

            if (session != null) {
                session.invalidate();
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/login"
            );

            return;
        }

        if ("/register".equals(path)) {

            request
                .getRequestDispatcher(
                    "/WEB-INF/views/auth/register.jsp"
                )
                .forward(
                    request,
                    response
                );

            return;
        }

        if ("/verify-otp".equals(path)) {

            request
                .getRequestDispatcher(
                    "/WEB-INF/views/auth/verify-otp.jsp"
                )
                .forward(
                    request,
                    response
                );

            return;
        }

        request
            .getRequestDispatcher(
                "/WEB-INF/views/auth/login.jsp"
            )
            .forward(
                request,
                response
            );
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
                   IOException {

        String path =
                request.getServletPath();

        if ("/register".equals(path)) {

            register(
                    request,
                    response
            );

            return;
        }

        if ("/verify-otp".equals(path)) {

            verifyOtp(
                    request,
                    response
            );

            return;
        }

        login(
                request,
                response
        );
    }

    private void login(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
                   IOException {

        String email =
                request.getParameter(
                        "email"
                );

        String password =
                request.getParameter(
                        "password"
                );

        Optional<User_24162119>
                result =
                userService.login(
                        email,
                        password
                );

        if (result.isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "Email hoặc mật khẩu không đúng"
            );

            request
                .getRequestDispatcher(
                    "/WEB-INF/views/auth/login.jsp"
                )
                .forward(
                    request,
                    response
                );

            return;
        }

        User_24162119 user =
                result.get();

        request.getSession()
                .setAttribute(
                        "currentUser",
                        user
                );

        if (Boolean.TRUE.equals(
                user.getIsAdmin())) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/books"
            );

        } else {

            response.sendRedirect(
                    request.getContextPath()
                            + "/home"
            );
        }
    }

    private void register(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
                   IOException {

        String email =
                request.getParameter(
                        "email"
                );

        String fullname =
                request.getParameter(
                        "fullname"
                );

        String phoneText =
                request.getParameter(
                        "phone"
                );

        String password =
                request.getParameter(
                        "password"
                );

        if (userService
                .existsByEmail(email)) {

            request.setAttribute(
                    "errorMessage",
                    "Email đã tồn tại"
            );

            request
                .getRequestDispatcher(
                    "/WEB-INF/views/auth/register.jsp"
                )
                .forward(
                    request,
                    response
                );

            return;
        }

        Integer phone = null;

        if (phoneText != null
                &&
                !phoneText.isBlank()) {

            phone =
                    Integer.valueOf(
                            phoneText
                    );
        }

        SecureRandom random =
                new SecureRandom();

        String otp =
                String.valueOf(
                    100000
                    +
                    random.nextInt(
                            900000
                    )
                );

        HttpSession session =
                request.getSession();

        session.setAttribute(
                "pendingEmail",
                email
        );

        session.setAttribute(
                "pendingFullname",
                fullname
        );

        session.setAttribute(
                "pendingPhone",
                phone
        );

        session.setAttribute(
                "pendingPassword",
                password
        );

        session.setAttribute(
                "otp",
                otp
        );

        session.setAttribute(
                "otpExpiredAt",
                LocalDateTime
                        .now()
                        .plusMinutes(5)
        );

        try {

            MailUtil_24162119
                    .sendOtp(
                            email,
                            otp
                    );

        } catch (Exception e) {

            request.setAttribute(
                    "errorMessage",
                    "Không gửi được OTP. "
                    + "Kiểm tra cấu hình mail."
            );

            request
                .getRequestDispatcher(
                    "/WEB-INF/views/auth/register.jsp"
                )
                .forward(
                    request,
                    response
                );

            return;
        }

        response.sendRedirect(
                request.getContextPath()
                        + "/verify-otp"
        );
    }

    private void verifyOtp(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
                   IOException {

        HttpSession session =
                request.getSession();

        String enteredOtp =
                request.getParameter(
                        "otp"
                );

        String correctOtp =
                (String)
                session.getAttribute(
                        "otp"
                );

        LocalDateTime expiredAt =
                (LocalDateTime)
                session.getAttribute(
                        "otpExpiredAt"
                );

        if (correctOtp == null
                ||
                expiredAt == null
                ||
                LocalDateTime.now()
                    .isAfter(expiredAt)
                ||
                !correctOtp.equals(
                        enteredOtp
                )) {

            request.setAttribute(
                    "errorMessage",
                    "OTP không đúng hoặc đã hết hạn"
            );

            request
                .getRequestDispatcher(
                    "/WEB-INF/views/auth/verify-otp.jsp"
                )
                .forward(
                    request,
                    response
                );

            return;
        }

        userService.register(
                (String)
                session.getAttribute(
                        "pendingEmail"
                ),

                (String)
                session.getAttribute(
                        "pendingFullname"
                ),

                (Integer)
                session.getAttribute(
                        "pendingPhone"
                ),

                (String)
                session.getAttribute(
                        "pendingPassword"
                )
        );

        session.removeAttribute("otp");
        session.removeAttribute(
                "otpExpiredAt"
        );

        session.removeAttribute(
                "pendingEmail"
        );

        session.removeAttribute(
                "pendingFullname"
        );

        session.removeAttribute(
                "pendingPhone"
        );

        session.removeAttribute(
                "pendingPassword"
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/login"
        );
    }
}