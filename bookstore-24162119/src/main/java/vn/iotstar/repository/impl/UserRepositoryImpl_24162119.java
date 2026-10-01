package vn.iotstar.repository.impl;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import vn.iotstar.entity.User_24162119;
import vn.iotstar.repository.UserRepository_24162119;
import vn.iotstar.util.JpaUtil_24162119;

public class
        UserRepositoryImpl_24162119
        implements
        UserRepository_24162119 {

    @Override
    public Optional<User_24162119>
            findByEmail(
                    String email) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        try {

            List<User_24162119>
                    users =
                    em.createQuery(
                            "SELECT u "
                            + "FROM User_24162119 u "
                            + "WHERE u.email = :email",
                            User_24162119.class
                    )
                    .setParameter(
                            "email",
                            email
                    )
                    .setMaxResults(1)
                    .getResultList();

            return users.stream()
                    .findFirst();

        } finally {

            em.close();
        }
    }

    @Override
    public User_24162119 save(
            User_24162119 user) {

        EntityManager em =
                JpaUtil_24162119
                        .getEntityManager();

        EntityTransaction transaction =
                em.getTransaction();

        try {

            transaction.begin();

            User_24162119 result;

            if (user.getId() == null) {

                em.persist(user);
                result = user;

            } else {

                result =
                        em.merge(user);
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
    public boolean existsByEmail(
            String email) {

        return findByEmail(email)
                .isPresent();
    }
}