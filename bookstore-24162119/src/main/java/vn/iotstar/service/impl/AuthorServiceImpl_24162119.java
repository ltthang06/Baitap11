package vn.iotstar.service.impl;

import java.time.LocalDate;
import java.util.List;

import vn.iotstar.entity.Author_24162119;
import vn.iotstar.model.PageResult_24162119;
import vn.iotstar.repository.AuthorRepository_24162119;
import vn.iotstar.repository.impl.AuthorRepositoryImpl_24162119;
import vn.iotstar.service.AuthorService_24162119;

public class
        AuthorServiceImpl_24162119
        implements
        AuthorService_24162119 {

    private final
            AuthorRepository_24162119
            authorRepository;

    public AuthorServiceImpl_24162119() {

        this.authorRepository =
                new AuthorRepositoryImpl_24162119();
    }

    @Override
    public PageResult_24162119<
            Author_24162119>
            findPage(
                    int page,
                    int size) {

        return authorRepository
                .findPage(page, size);
    }

    @Override
    public List<Author_24162119>
            findAll() {

        return authorRepository
                .findAll();
    }

    @Override
    public Author_24162119 findById(
            Integer id) {

        Author_24162119 author =
                authorRepository
                        .findById(id);

        if (author == null) {

            throw new
                    IllegalArgumentException(
                            "Khong tim thay tac gia"
                    );
        }

        return author;
    }

    @Override
    public Author_24162119 save(
            Integer id,
            String authorName,
            LocalDate dateOfBirth) {

        Author_24162119 author;

        if (id == null) {

            author =
                    new Author_24162119();

        } else {

            author = findById(id);
        }

        author.setAuthorName(
                authorName
        );

        author.setDateOfBirth(
                dateOfBirth
        );

        return authorRepository
                .save(author);
    }

    @Override
    public void delete(
            Integer id) {

        authorRepository
                .delete(id);
    }
}