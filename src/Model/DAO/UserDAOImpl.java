package src.Model.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import src.CustomExceptions.DuplicateUser;
import src.Model.User;

public class UserDAOImpl implements UserDAO {
    
    public void createUser(User user) throws DuplicateUser, SQLException{
        DatabaseConnection dbCon = new DatabaseConnection();
        try{
            Statement statement = dbCon.con.createStatement();
            String sqlQuery = String.format("INSERT INTO User VALUES ('%s', '%s');", user.getUsername(), user.getPassword());
            statement.executeUpdate(sqlQuery);
            dbCon.close();

        } catch(SQLException e){
            System.out.println("Error while connecting to the database");
            System.out.println(e.getMessage());
            dbCon.close();

            if(e.getMessage().contains("[SQLITE_CONSTRAINT_PRIMARYKEY]")){
                throw new DuplicateUser();
            }
            throw e;
        }
    }

    public User getUserByCredentials(String username, String password){
        DatabaseConnection dbCon = new DatabaseConnection();
        try{
            Statement statement = dbCon.con.createStatement();
            String sqlQuery = String.format("SELECT * FROM User WHERE username = '%s' AND password = '%s';", username, password);
            ResultSet createResult = statement.executeQuery(sqlQuery);
            System.out.println(createResult);
            User user = createUserInstance(createResult);
            dbCon.close();
            return user;


        } catch(SQLException e){
            System.out.println("Error while connecting to the database");
            System.out.println(e.getMessage());
            dbCon.close();
            return null;
        }
    }

    public void updateUser(User user){

    }

    private User createUserInstance(ResultSet queryResult) throws SQLException{
        if(!queryResult.next()){
            return null;
        }
        String username = (String) queryResult.getObject("username");
        String password = (String) queryResult.getObject("password");
        return new User(username, password);
    }
}
