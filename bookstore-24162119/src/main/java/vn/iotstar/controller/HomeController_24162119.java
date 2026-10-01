package vn.iotstar.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.iotstar.entity.Book_24162119;
import vn.iotstar.model.PageResult_24162119;
import vn.iotstar.service.BookService_24162119;
import vn.iotstar.service.RatingService_24162119;
import vn.iotstar.service.impl.BookServiceImpl_24162119;
import vn.iotstar.service.impl.RatingServiceImpl_24162119;

@WebServlet(
    urlPatterns = {
        "/home",
        "/products"
    }
)
public class
        HomeController_24162119
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

        int page = 0;

        try {

            page =
                Integer.parseInt(
                    request.getParameter(
                            "page"
                    )
                );

        } catch (Exception ignored) {
        }

        PageResult_24162119<
                Book_24162119>
                books =
                bookService
                        .findPage(
                                page,
                                6
                        );

        Map<Integer, Long>
                reviewCounts =
                new HashMap<>();

        for (Book_24162119 book
                : books.getContent()) {

            reviewCounts.put(
                    book.getBookid(),

                    ratingService
                            .countByBookId(
                                    book.getBookid()
                            )
            );
        }

        request.setAttribute(
                "books",
                books
        );

        request.setAttribute(
                "reviewCounts",
                reviewCounts
        );

        request
            .getRequestDispatcher(
                "/WEB-INF/views/user/home.jsp"
            )
            .forward(
                request,
                response
            );
    }
}