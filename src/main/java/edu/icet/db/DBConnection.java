package edu.icet.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static DBConnection instance;

    public Connection connection;

    private DBConnection() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ticketmaster_db", "root", "1234");
    }

    public static DBConnection getInstance() throws SQLException {
        if (instance == null){
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}
