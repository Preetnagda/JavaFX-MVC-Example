package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    public Connection con;
    public Statement statement;

    public DatabaseConnection() {

        try {
            // Database URL, assuming the database file is in the project directory
            String url = "jdbc:sqlite:database.db";

            // Establish the connection
            con = DriverManager.getConnection(url);

            System.out.println("Connected to SQLite database.");
        } catch (SQLException e) {
            System.err.println("Error connecting to SQLite database: " + e.getMessage());
        }
    }
}
