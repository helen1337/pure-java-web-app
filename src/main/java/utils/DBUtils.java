package utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
}
