package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    /*
     * Establishes and returns a connection to the Databases.
     */
    public static Connection getDatabaseConnection() throws SQLException, ClassNotFoundException {
        Class.forName(StringUtils.DRIVER_NAME);
        return DriverManager.getConnection(StringUtils.DB_URL, StringUtils.USER, StringUtils.PASS);
    }

}