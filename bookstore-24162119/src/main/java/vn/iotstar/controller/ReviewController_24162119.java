package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.iotstar.entity.User_24162119;
import vn.iotstar.service.RatingService_24162119;
import vn.iotstar.service.impl.RatingServiceImpl_24162119;

@WebServlet("/review/add")
public class
        ReviewController_24162119
        extends HttpServlet {

    private static final long
            serialVersionUID = 1L;

    private RatingService_24162119
            ratingService;

    @Override
    public void init() {

        ratingService =
                new RatingServiceImpl_24162119();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
                   IOException {

        User_24162119 user =
                (User_24162119)
                request.getSession()
                        .getAttribute(
                                "currentUser"
                        );

        Integer bookId =
                Integer.valueOf(
                    request.getParameter(
                            "bookId"
                    )
                );

        if (user == null) {

            response.sendRedirect(
                    request.getContextPath()
                            + "/login"
            );

            return;
        }

        Byte rating =
                Byte.valueOf(
                    request.getParameter(
                            "rating"
                    )
                );

        String reviewText =
                request.getParameter(
                        "reviewText"
                );

        ratingService.addReview(
                user.getId(),
                bookId,
                rating,
                reviewText
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/book-detail?id="
                        + bookId
        );
    }
}