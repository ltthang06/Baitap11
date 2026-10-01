package vn.iotstar.repository.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import vn.iotstar.entity.Book_24162119;
import vn.iotstar.model.PageResult_24162119;
import vn.iotstar.repository.BookRepository_24162119;
import vn.iotstar.util.JpaUtil_24162119;

public class
        BookRepositoryImpl_24162119
        implements
        BookRepository_24162119 {

    @Override
    public PageResult_24162119<
            Book_24162119>
            findPage(
                    int page,
                    int size) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        try {

            long total =
                    em.createQuery(
                            "SELECT COUNT(b) "
                            + "FROM Book_24162119 b",
                            Long.class
                    )
                    .getSingleResult();

            List<Book_24162119>
                    content =
                    em.createQuery(
                            "SELECT b "
                            + "FROM Book_24162119 b "
                            + "ORDER BY b.bookid DESC",
                            Book_24162119.class
                    )
                    .setFirstResult(
                            page * size
                    )
                    .setMaxResults(size)
                    .getResultList();

            for (Book_24162119 book
                    : content) {

                book.getAuthors().size();
            }

            return new
                    PageResult_24162119<>(
                            content,
                            page,
                            size,
                            total
                    );

        } finally {

            em.close();
        }
    }

    @Override
    public Book_24162119 findById(
            Integer id) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        try {

            Book_24162119 book =
                    em.find(
                            Book_24162119.class,
                            id
                    );

            if (book != null) {
                book.getAuthors().size();
            }

            return book;

        } finally {

            em.close();
        }
    }

    @Override
    public Book_24162119 save(
            Book_24162119 book) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        EntityTransaction transaction =
                em.getTransaction();

        try {

            transaction.begin();

            Book_24162119 result;

            if (book.getBookid()
                    == null) {

                em.persist(book);
                result = book;

            } else {

                result =
                        em.merge(book);
            }

            transaction.commit();

            return result;

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;

        } finally {

            em.close();
        }
    }

    @Override
    public void delete(
            Integer id) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        EntityTransaction transaction =
                em.getTransaction();

        try {

            transaction.begin();

            Book_24162119 book =
                    em.find(
                            Book_24162119.class,
                            id
                    );

            if (book != null) {
                em.remove(book);
            }

            transaction.commit();

        } catch (Exception e) {

            if (transaction.isActive()) {
                transaction.rollback();
            }

            throw e;

        } finally {

            em.close();
        }
    }
}