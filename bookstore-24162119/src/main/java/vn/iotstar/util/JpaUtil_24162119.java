package vn.iotstar.util;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil_24162119 {

    private static final EntityManagerFactory
            entityManagerFactory =
            createEntityManagerFactory();

    private static EntityManagerFactory
            createEntityManagerFactory() {

        Map<String, Object> properties =
                new HashMap<>();

        String dbUrl =
                System.getenv()
                        .getOrDefault(
                                "DB_URL",
                                "jdbc:sqlserver://localhost:1433;"
                                + "databaseName=BookStore_24162119;"
                                + "encrypt=true;"
                                + "trustServerCertificate=true"
                        );

        String dbUsername =
                System.getenv()
                        .getOrDefault(
                                "DB_USERNAME",
                                "sa"
                        );

        String dbPassword =
                System.getenv()
                        .getOrDefault(
                                "DB_PASSWORD",
                                ""
                        );

        properties.put(
                "jakarta.persistence.jdbc.driver",
                "com.microsoft.sqlserver.jdbc.SQLServerDriver"
        );

        properties.put(
                "jakarta.persistence.jdbc.url",
                dbUrl
        );

        properties.put(
                "jakarta.persistence.jdbc.user",
                dbUsername
        );

        properties.put(
                "jakarta.persistence.jdbc.password",
                dbPassword
        );

        return Persistence
                .createEntityManagerFactory(
                        "BookStorePU",
                        properties
                );
    }

    public static EntityManager
            getEntityManager() {

        return entityManagerFactory
                .createEntityManager();
    }

    public static void close() {

        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}