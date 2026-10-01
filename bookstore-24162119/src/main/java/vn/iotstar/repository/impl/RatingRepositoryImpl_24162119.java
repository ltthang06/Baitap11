package vn.iotstar.repository.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import vn.iotstar.entity.RatingId_24162119;
import vn.iotstar.entity.Rating_24162119;
import vn.iotstar.repository.RatingRepository_24162119;
import vn.iotstar.util.JpaUtil_24162119;

public class
        RatingRepositoryImpl_24162119
        implements
        RatingRepository_24162119 {

    @Override
    public List<Rating_24162119>
            findByBookId(
                    Integer bookId) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        try {

            return em.createQuery(
                    "SELECT r "
                    + "FROM Rating_24162119 r "
                    + "JOIN FETCH r.user "
                    + "WHERE r.id.bookid = :bookId",
                    Rating_24162119.class
            )
            .setParameter(
                    "bookId",
                    bookId
            )
            .getResultList();

        } finally {

            em.close();
        }
    }

    @Override
    public long countByBookId(
            Integer bookId) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        try {

            return em.createQuery(
                    "SELECT COUNT(r) "
                    + "FROM Rating_24162119 r "
                    + "WHERE r.id.bookid = :bookId",
                    Long.class
            )
            .setParameter(
                    "bookId",
                    bookId
            )
            .getSingleResult();

        } finally {

            em.close();
        }
    }

    @Override
    public Optional<Rating_24162119>
            findById(
                    RatingId_24162119 id) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        try {

            return Optional.ofNullable(
                    em.find(
                            Rating_24162119.class,
                            id
                    )
            );

        } finally {

            em.close();
        }
    }

    @Override
    public Rating_24162119 save(
            Rating_24162119 rating) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        EntityTransaction transaction =
                em.getTransaction();

        try {

            transaction.begin();

            Rating_24162119 result =
                    em.merge(rating);

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
}