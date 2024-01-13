package utils;

import java.sql.*;

public class DBUtils {
    public static void close(Statement st) throws SQLException {

        if (st != null)
            st.close();
    }

    public static void close(Statement st, ResultSet rs) throws SQLException {

        if (st != null)
            st.close();
        if (rs != null)
            rs.close();
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String driver = AppConfigurationLoader.getProperty("driverDB");
        String url = AppConfigurationLoader.getProperty("urlDB");
        String user = AppConfigurationLoader.getProperty("userDB");
        String password = AppConfigurationLoader.getProperty("passwordDB");

        Class.forName(driver);
        return DriverManager.getConnection(url, user, password);
    }
}
