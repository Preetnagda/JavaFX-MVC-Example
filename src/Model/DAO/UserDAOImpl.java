package src.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import src.model.AuthUser;
import src.model.User;
import src.custom_exception.DuplicateUser;

/**
 * UserDAOImpl implements the UserDAO interface and provides methods for interacting
 * with the database to create, retrieve, update, and check user information.
 */
public class UserDAOImpl implements UserDAO {

    /**
     * Create a new user in the database.
     *
     * @param user The User object to be created in the database.
     * @throws DuplicateUser Thrown if a user with the same primary key (username) already exists in the database.
     */
    public void createUser(User user) throws DuplicateUser, SQLException {
        DatabaseConnection dbCon = createDatabaseConnection();
        try {
            Statement statement = dbCon.con.createStatement();
            String sqlQuery = String.format("INSERT INTO User (username, password, firstname, lastname) VALUES ('%s', '%s', '%s', '%s');",
                    user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname());
            statement.executeUpdate(sqlQuery);
            dbCon.close();
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
            System.out.println(e.getMessage());
            dbCon.close();

            if (e.getMessage().contains("[SQLITE_CONSTRAINT_PRIMARYKEY]")) {
                throw new DuplicateUser();
            }
            throw e;
        }
    }

    /**
     * Retrieve an AuthUser object based on the provided username and password.
     *
     * @param username The username of the user to retrieve.
     * @param password The password of the user to retrieve.
     * @return The retrieved AuthUser object or null if not found.
     */
    public AuthUser getUserByCredentials(String username, String password) {
        DatabaseConnection dbCon = createDatabaseConnection();
        try {
            Statement statement = dbCon.con.createStatement();
            String sqlQuery = String.format("SELECT * FROM User WHERE username = '%s' AND password = '%s';", username, password);
            ResultSet createResult = statement.executeQuery(sqlQuery);
            AuthUser user = createUserInstance(createResult);
            dbCon.close();
            return user;
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
            System.out.println(e.getMessage());
            dbCon.close();
            return null;
        }
    }

    /**
     * Update an existing user's information in the database.
     *
     * @param username The username of the user to update.
     * @param user     The updated AuthUser object with new information.
     * @throws DuplicateUser Thrown if there is a conflict with unique constraints.
     */
    public void updateUser(String username, AuthUser user) throws DuplicateUser, SQLException {
        DatabaseConnection dbCon = createDatabaseConnection();
        try {
            Statement statement = dbCon.con.createStatement();
            String sqlQuery = String.format("""
                UPDATE User 
                SET username = '%s',
                password = '%s',
                firstname = '%s',
                lastname = '%s'
                WHERE username = '%s';
                """, user.getUsername(), user.getPassword(), user.getFirstname(), user.getLastname(), username);

            statement.executeUpdate(sqlQuery);
            dbCon.close();

        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
            System.out.println(e.getMessage());
            dbCon.close();

            if (e.getMessage().contains("[SQLITE_CONSTRAINT_UNIQUE]")) {
                throw new DuplicateUser();
            }
            throw e;
        }
    }

    /**
     * Update the VIP status of a user in the database.
     *
     * @param user       The AuthUser object for which to update the VIP status.
     * @param vipStatus  The new VIP status to set.
     * @return The updated AuthUser object with the new VIP status.
     * @throws SQLException Thrown if there is an issue with the database update.
     */
    public AuthUser updateUserVipStatus(AuthUser user, Integer vipStatus) throws SQLException {
        DatabaseConnection dbCon = createDatabaseConnection();
        try {
            Statement statement = dbCon.con.createStatement();
            String sqlQuery = String.format("""
                UPDATE User 
                SET is_vip = '%d'
                WHERE username = '%s';
                """, vipStatus, user.getUsername());

            statement.executeUpdate(sqlQuery);
            AuthUser newUser = new AuthUser(user.getIsVip(), user.getFirstname(), user.getLastname(), user.getUsername(), user.getPassword(), vipStatus);
            dbCon.close();
            return newUser;
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
            System.out.println(e.getMessage());
            dbCon.close();
            throw e;
        }
    }

    /**
     * Check if a username already exists in the database.
     *
     * @param username The username to check.
     * @return true if the username exists, false if it does not.
     */
    public Boolean isUsernameExists(String username) {
        DatabaseConnection dbCon = createDatabaseConnection();
        try {
            Statement statement = dbCon.con.createStatement();
            String sqlQuery = String.format("SELECT count(id) FROM User WHERE username = '%s'", username);
            ResultSet result = statement.executeQuery(sqlQuery);
            if (result.next()) {
                if (result.getInt(0) != 0) {
                    return true;
                }
            }
            dbCon.close();
            return false;
        } catch (SQLException e) {
            System.out.println("Error while connecting to the database");
            System.out.println(e.getMessage());
            dbCon.close();
            return null;
        }
    }

    /**
     * Create an AuthUser instance from the ResultSet query result.
     *
     * @param queryResult The ResultSet containing user data.
     * @return The created AuthUser object or null if the ResultSet is empty.
     * @throws SQLException Thrown if there is an issue with parsing the data.
     */
    private AuthUser createUserInstance(ResultSet queryResult) throws SQLException {
        if (!queryResult.next()) {
            return null;
        }
        Integer id = (Integer) queryResult.getObject("id");
        String firstname = (String) queryResult.getObject("firstname");
        String lastname = (String) queryResult.getObject("lastname");
        String username = (String) queryResult.getObject("username");
        String password = (String) queryResult.getObject("password");
        Integer isVip = (Integer) queryResult.getObject("is_vip");

        return new AuthUser(id, firstname, lastname, username, password, isVip);
    }

    public DatabaseConnection createDatabaseConnection() {
        return new DatabaseConnection();
    }
}
