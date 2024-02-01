package utils;

import java.sql.*;

/**
 * Utility class for managing database connections.
 */
public class DBUtils {

    /**
     * Retrieves a database connection using the properties specified in the configuration file.
     *
     * @see AppConfigurationLoader
     * @return A database connection.
     * @throws ClassNotFoundException If the database driver class is not found.
     * @throws SQLException           If a database access error occurs.
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // Load database configuration properties
        String driver = AppConfigurationLoader.getProperty("driverDB");
        String url = AppConfigurationLoader.getProperty("urlDB");
        String user = AppConfigurationLoader.getProperty("userDB");
        String password = AppConfigurationLoader.getProperty("passwordDB");
        // Register the JDBC driver
        Class.forName(driver);
        // Establish the database connection
        return DriverManager.getConnection(url, user, password);
    }
}