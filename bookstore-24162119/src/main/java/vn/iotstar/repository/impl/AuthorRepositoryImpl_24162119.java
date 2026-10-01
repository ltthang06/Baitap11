package vn.iotstar.repository.impl;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import vn.iotstar.entity.Author_24162119;
import vn.iotstar.model.PageResult_24162119;
import vn.iotstar.repository.AuthorRepository_24162119;
import vn.iotstar.util.JpaUtil_24162119;

public class
        AuthorRepositoryImpl_24162119
        implements
        AuthorRepository_24162119 {

    @Override
    public PageResult_24162119<
            Author_24162119>
            findPage(
                    int page,
                    int size) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        try {

            long total =
                    em.createQuery(
                            "SELECT COUNT(a) "
                            + "FROM Author_24162119 a",
                            Long.class
                    )
                    .getSingleResult();

            List<Author_24162119>
                    content =
                    em.createQuery(
                            "SELECT a "
                            + "FROM Author_24162119 a "
                            + "ORDER BY a.authorId DESC",
                            Author_24162119.class
                    )
                    .setFirstResult(
                            page * size
                    )
                    .setMaxResults(size)
                    .getResultList();

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
    public List<Author_24162119>
            findAll() {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        try {

            return em.createQuery(
                    "SELECT a "
                    + "FROM Author_24162119 a "
                    + "ORDER BY a.authorName",
                    Author_24162119.class
            ).getResultList();

        } finally {

            em.close();
        }
    }

    @Override
    public List<Author_24162119>
            findAllByIds(
                    List<Integer> ids) {

        if (ids == null
                || ids.isEmpty()) {

            return new ArrayList<>();
        }

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        try {

            return em.createQuery(
                    "SELECT a "
                    + "FROM Author_24162119 a "
                    + "WHERE a.authorId IN :ids",
                    Author_24162119.class
            )
            .setParameter(
                    "ids",
                    ids
            )
            .getResultList();

        } finally {

            em.close();
        }
    }

    @Override
    public Author_24162119
            findById(
                    Integer id) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        try {

            return em.find(
                    Author_24162119.class,
                    id
            );

        } finally {

            em.close();
        }
    }

    @Override
    public Author_24162119 save(
            Author_24162119 author) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        EntityTransaction transaction =
                em.getTransaction();

        try {

            transaction.begin();

            Author_24162119 result;

            if (author.getAuthorId()
                    == null) {

                em.persist(author);
                result = author;

            } else {

                result =
                        em.merge(author);
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

            Author_24162119 author =
                    em.find(
                            Author_24162119.class,
                            id
                    );

            if (author != null) {
                em.remove(author);
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