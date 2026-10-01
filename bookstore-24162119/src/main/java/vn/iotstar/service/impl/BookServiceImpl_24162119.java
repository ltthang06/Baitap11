package vn.iotstar.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import vn.iotstar.entity.Author_24162119;
import vn.iotstar.entity.Book_24162119;
import vn.iotstar.model.PageResult_24162119;
import vn.iotstar.repository.AuthorRepository_24162119;
import vn.iotstar.repository.BookRepository_24162119;
import vn.iotstar.repository.impl.AuthorRepositoryImpl_24162119;
import vn.iotstar.repository.impl.BookRepositoryImpl_24162119;
import vn.iotstar.service.BookService_24162119;

public class
        BookServiceImpl_24162119
        implements
        BookService_24162119 {

    private final
            BookRepository_24162119
            bookRepository;

    private final
            AuthorRepository_24162119
            authorRepository;

    public BookServiceImpl_24162119() {

        this.bookRepository =
                new BookRepositoryImpl_24162119();

        this.authorRepository =
                new AuthorRepositoryImpl_24162119();
    }

    @Override
    public PageResult_24162119<
            Book_24162119>
            findPage(
                    int page,
                    int size) {

        return bookRepository
                .findPage(
                        page,
                        size
                );
    }

    @Override
    public Book_24162119 findById(
            Integer id) {

        Book_24162119 book =
                bookRepository
                        .findById(id);

        if (book == null) {

            throw new
                    IllegalArgumentException(
                            "Khong tim thay sach"
                    );
        }

        return book;
    }

    @Override
    public Book_24162119 save(
            Integer id,
            Integer isbn,
            String title,
            String publisher,
            BigDecimal price,
            String description,
            LocalDate publishDate,
            String coverImage,
            Integer quantity,
            List<Integer> authorIds) {

        Book_24162119 book;

        if (id == null) {

            book =
                    new Book_24162119();

        } else {

            book =
                    findById(id);
        }

        book.setIsbn(isbn);
        book.setTitle(title);
        book.setPublisher(publisher);
        book.setPrice(price);
        book.setDescription(description);
        book.setPublishDate(publishDate);
        book.setCoverImage(coverImage);
        book.setQuantity(quantity);

        List<Author_24162119>
                authors =
                authorRepository
                        .findAllByIds(
                                authorIds
                        );

        book.setAuthors(
                new HashSet<>(
                        authors
                )
        );

        return bookRepository
                .save(book);
    }

    @Override
    public void delete(
            Integer id) {

        bookRepository.delete(id);
    }
}