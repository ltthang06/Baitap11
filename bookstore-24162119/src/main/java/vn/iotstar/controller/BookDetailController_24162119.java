package vn.iotstar.controller;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.iotstar.entity.Book_24162119;
import vn.iotstar.service.BookService_24162119;
import vn.iotstar.service.RatingService_24162119;
import vn.iotstar.service.impl.BookServiceImpl_24162119;
import vn.iotstar.service.impl.RatingServiceImpl_24162119;

@WebServlet("/book-detail")
public class
        BookDetailController_24162119
        extends HttpServlet {

    private static final long
            serialVersionUID = 1L;

    private BookService_24162119
            bookService;

    private RatingService_24162119
            ratingService;

    @Override
    public void init() {

        bookService =
                new BookServiceImpl_24162119();

        ratingService =
                new RatingServiceImpl_24162119();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
                   IOException {

        Integer id =
                Integer.valueOf(
                    request.getParameter(
                            "id"
                    )
                );

        Book_24162119 book =
                bookService
                        .findById(id);

        request.setAttribute(
                "book",
                book
        );

        request.setAttribute(
                "reviews",
                ratingService
                        .findByBookId(id)
        );

        request.setAttribute(
                "reviewCount",
                ratingService
                        .countByBookId(id)
        );

        request
            .getRequestDispatcher(
                "/WEB-INF/views/user/book-detail.jsp"
            )
            .forward(
                request,
                response
            );
    }
}