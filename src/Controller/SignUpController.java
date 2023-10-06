package src.Controller;

import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import src.CustomExceptions.DuplicateUser;
import src.Model.User;
import src.Model.DAO.UserDAO;
import src.Model.DAO.UserDAOImpl;

public class SignUpController extends Controller{

    @FXML
    private TextField firstname;
    @FXML
    private TextField lastname;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;

    @FXML
    public void onSignUpRequest(ActionEvent event){
        String inputFirstname = firstname.getText();
        String inputLastname = lastname.getText();
        String inputUsername = username.getText();
        String inputPassword = password.getText();
        String inputConfirmPassword = confirmPassword.getText();

        if(inputFirstname.isEmpty() ||
            inputLastname.isEmpty() ||
            inputUsername.isEmpty() ||
            inputPassword.isEmpty() ||
            inputConfirmPassword.isEmpty()){
                generateError("Fields cannot be empty");
                return;
            }
        if(!inputPassword.equals(inputConfirmPassword)){
            generateError("Passwords do not match");
            return;
        }
        UserDAO userdao = new UserDAOImpl();
        User newUser = new User(inputFirstname, inputLastname, inputUsername, inputPassword);

        try {
            userdao.createUser(newUser);
            System.out.println("User Registered");
            switchScene("Login");
        } catch (DuplicateUser e) {
            generateError("Username already exists, please try a different username");
        } catch (SQLException e){
            generateError("User cannot be created at this time, please try again later");
        }
    }

    @FXML
    public void login(ActionEvent event){
       switchScene("Login");
    }
}
