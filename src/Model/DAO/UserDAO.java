package src.model.dao;

import java.sql.SQLException;

import src.model.AuthUser;
import src.model.User;
import src.custom_exception.DuplicateUser;

public interface UserDAO {
    void createUser(User user) throws DuplicateUser, SQLException;
    AuthUser getUserByCredentials(String username, String password);
    void updateUser(String username, AuthUser user) throws DuplicateUser, SQLException;
    Boolean isUsernameExists(String username);
}