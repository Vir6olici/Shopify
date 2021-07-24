package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseProvider {
    private static Connection connection;

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/db-schema?serverTimezone=UTC","root","root");
        connection.setAutoCommit(false);
        return connection;
    }

}
