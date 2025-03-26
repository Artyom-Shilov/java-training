package com.shilov.common.connectivity;

import com.shilov.common.properties.PropertyReader;

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
}
