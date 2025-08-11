package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final String URL = "jdbc:mysql://localhost:3306/mystery_solver";    

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }
    public static String getUrl() {
        return URL;
    }
}
