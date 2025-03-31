package com.shilov.common.connectivity;

import com.shilov.common.properties.PropertyReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionManager {

    private DatabaseConnectionManager() {}

    public static Connection getJdbcConnection() throws IOException, SQLException {
        return DriverManager.getConnection(
                PropertyReader.getProperty(PropertyReader.DB_URL),
                PropertyReader.getProperty(PropertyReader.DB_USER),
                PropertyReader.getProperty(PropertyReader.DB_PASSWORD));
    }

    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("Space Reservation");

    public static EntityManager getEntityManager() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }
}
