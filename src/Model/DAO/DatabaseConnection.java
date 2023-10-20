package src.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection con;

    public DatabaseConnection() {

        try {
            // Database URL, assuming the database file is in the project directory
            String url = "jdbc:sqlite:datahub.db";

            // Establish the connection
            con = DriverManager.getConnection(url);

            System.out.println("Connected to SQLite database.");
        } catch (SQLException e) {
            System.err.println("Error connecting to SQLite database: " + e.getMessage());
        }
    }

    public void close(){
        try {
            con.close();
        } catch (SQLException e) {
            System.out.println("Error while closing the connection");
        }
        
    }
}
