package vn.iotstar.controller;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import vn.iotstar.entity.Author_24162119;
import vn.iotstar.service.AuthorService_24162119;
import vn.iotstar.service.impl.AuthorServiceImpl_24162119;

@WebServlet(
    urlPatterns = {
        "/admin/authors",
        "/admin/authors/form",
        "/admin/authors/save",
        "/admin/authors/delete"
    }
)
public class
        AuthorController_24162119
        extends HttpServlet {

    private static final long
            serialVersionUID = 1L;

    private AuthorService_24162119
            authorService;

    @Override
    public void init() {

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

        if ("/admin/authors/form"
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
            throws IOException {

        String path =
                request.getServletPath();

        if ("/admin/authors/delete"
                .equals(path)) {

            authorService.delete(
                    Integer.valueOf(
                        request.getParameter(
                                "id"
                        )
                    )
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/authors"
            );

            return;
        }

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

        String dateText =
                request.getParameter(
                        "dateOfBirth"
                );

        LocalDate dateOfBirth =
                dateText == null
                || dateText.isBlank()
                ? null
                : LocalDate.parse(
                        dateText
                );

        authorService.save(
                id,

                request.getParameter(
                        "authorName"
                ),

                dateOfBirth
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/authors"
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
                "authors",
                authorService
                        .findPage(
                                page,
                                5
                        )
        );

        request
            .getRequestDispatcher(
                "/WEB-INF/views/admin/author-list.jsp"
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

        Author_24162119 author =
                new Author_24162119();

        if (idText != null
                &&
                !idText.isBlank()) {

            author =
                    authorService
                            .findById(
                                Integer.valueOf(
                                        idText
                                )
                            );
        }

        request.setAttribute(
                "author",
                author
        );

        request
            .getRequestDispatcher(
                "/WEB-INF/views/admin/author-form.jsp"
            )
            .forward(
                request,
                response
            );
    }
}