package src.Model.DAO;

import java.sql.SQLException;

import src.CustomExceptions.DuplicateUser;
import src.Model.AuthUser;
import src.Model.User;

public interface UserDAO {
    void createUser(User user) throws DuplicateUser, SQLException;
    AuthUser getUserByCredentials(String username, String password);
    void updateUser(String username, AuthUser user) throws DuplicateUser, SQLException;
    Boolean isUsernameExists(String username);
}