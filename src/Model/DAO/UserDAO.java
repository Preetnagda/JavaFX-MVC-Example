package src.Model.DAO;

import java.sql.SQLException;

import src.CustomExceptions.DuplicateUser;
import src.Model.User;

public interface UserDAO {
    void createUser(User user) throws DuplicateUser, SQLException;
    User getUserByCredentials(String username, String password);
    void updateUser(String username, User user) throws DuplicateUser, SQLException;
    Boolean isUsernameExists(String username);
}