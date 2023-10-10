package src.Controller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import src.CustomExceptions.DuplicateUser;
import src.Model.User;
import src.Model.DAO.UserDAOImpl;

public class UpdateProfileController extends LoggedInController {
    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField username;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private PasswordField newPassword;

    @Override
    public void setUser(User user){
        super.setUser(user);
        firstname.setText(user.getFirstname());
        lastname.setText(user.getLastname());
        username.setText(user.getUsername());
    }

    @FXML
    public void cancelUserUpdate(ActionEvent event){
        switchScene("Dashboard", user);
    }

    @FXML
    public void updateRequest(ActionEvent event){
        if(!isAuth() || !validateUserInput()){
            return;
        }
        String inputFirstname = firstname.getText();
        String inputLastname = lastname.getText();
        String inputUsername = username.getText();
        String inputNewPassword = newPassword.getText();
        if(inputNewPassword.isEmpty()){
            inputNewPassword = user.getPassword();
        }
        User updatedUser = new User(inputFirstname, inputLastname, inputUsername, inputNewPassword, user.getIsVip());
        try {
            new UserDAOImpl().updateUser(user.getUsername(), updatedUser);
            switchScene("Dashboard", updatedUser);
        } catch (DuplicateUser e) {
            generateError("Duplicate Username. Please try again with a different username.");
        } catch (SQLException e){
            generateError("Unable to complete request currently.");
        }
    }

    private Boolean validateUserInput(){
        String inputFirstname = firstname.getText();
        String inputLastname = lastname.getText();
        String inputUsername = username.getText();

        if(inputFirstname.isEmpty() ||
        inputLastname.isEmpty() ||
        inputUsername.isEmpty()){
            generateError("Fields cannot be empty");
            return false;
        }
        
        return true;
    }
}
