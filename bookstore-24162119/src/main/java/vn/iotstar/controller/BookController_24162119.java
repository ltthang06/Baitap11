package vn.iotstar.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.iotstar.entity.Author_24162119;
import vn.iotstar.entity.Book_24162119;
import vn.iotstar.service.AuthorService_24162119;
import vn.iotstar.service.BookService_24162119;
import vn.iotstar.service.impl.AuthorServiceImpl_24162119;
import vn.iotstar.service.impl.BookServiceImpl_24162119;

@WebServlet(
    urlPatterns = {
        "/admin/books",
        "/admin/books/form",
        "/admin/books/save",
        "/admin/books/delete"
    }
)
public class
        BookController_24162119
        extends HttpServlet {

    private static final long
            serialVersionUID = 1L;

    private BookService_24162119
            bookService;

    private AuthorService_24162119
            authorService;

    @Override
    public void init() {

        bookService =
                new BookServiceImpl_24162119();

        authorService =
                new AuthorServiceImpl_24162119();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
                   IOException {

        String path =
                request.getServletPath();

        if ("/admin/books/form"
                .equals(path)) {

            showForm(
                    request,
                    response
            );

            return;
        }

        showList(
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

        if ("/admin/books/delete"
                .equals(path)) {

            Integer id =
                    Integer.valueOf(
                        request.getParameter(
                                "id"
                        )
                    );

            bookService.delete(id);

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/books"
            );

            return;
        }

        save(
                request,
                response
        );
    }

    private void showList(
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

        request.setAttribute(
                "books",
                bookService
                        .findPage(
                                page,
                                5
                        )
        );

        request
            .getRequestDispatcher(
                "/WEB-INF/views/admin/book-list.jsp"
            )
            .forward(
                request,
                response
            );
    }

    private void showForm(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException,
                   IOException {

        String idText =
                request.getParameter(
                        "id"
                );

        Book_24162119 book =
                new Book_24162119();

        Set<Integer>
                selectedAuthorIds =
                new HashSet<>();

        if (idText != null
                &&
                !idText.isBlank()) {

            book =
                    bookService
                            .findById(
                                    Integer.valueOf(
                                            idText
                                    )
                            );

            for (Author_24162119 author
                    : book.getAuthors()) {

                selectedAuthorIds.add(
                        author.getAuthorId()
                );
            }
        }

        request.setAttribute(
                "book",
                book
        );

        request.setAttribute(
                "authors",
                authorService.findAll()
        );

        request.setAttribute(
                "selectedAuthorIds",
                selectedAuthorIds
        );

        request
            .getRequestDispatcher(
                "/WEB-INF/views/admin/book-form.jsp"
            )
            .forward(
                request,
                response
            );
    }

    private void save(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        String idText =
                request.getParameter(
                        "id"
                );

        Integer id =
                idText == null
                || idText.isBlank()
                ? null
                : Integer.valueOf(
                        idText
                );

        String[] values =
                request.getParameterValues(
                        "authorIds"
                );

        List<Integer> authorIds =
                new ArrayList<>();

        if (values != null) {

            for (String value
                    : values) {

                authorIds.add(
                        Integer.valueOf(
                                value
                        )
                );
            }
        }

        bookService.save(
                id,

                Integer.valueOf(
                    request.getParameter(
                            "isbn"
                    )
                ),

                request.getParameter(
                        "title"
                ),

                request.getParameter(
                        "publisher"
                ),

                new BigDecimal(
                    request.getParameter(
                            "price"
                    )
                ),

                request.getParameter(
                        "description"
                ),

                LocalDate.parse(
                    request.getParameter(
                            "publishDate"
                    )
                ),

                request.getParameter(
                        "coverImage"
                ),

                Integer.valueOf(
                    request.getParameter(
                            "quantity"
                    )
                ),

                authorIds
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/books"
        );
    }
}