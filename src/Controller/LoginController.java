package src.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import src.Model.User;
import src.Model.DAO.UserDAO;
import src.Model.DAO.UserDAOImpl;

public class LoginController extends Controller{

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    public void onLoginRequest(ActionEvent event){
        String inputUsername = username.getText();
        String inputPassword = password.getText();
        if(inputUsername.isEmpty() || inputPassword.isEmpty()){
            generateError("Username or password cannot be empty");
            return;
        }
        UserDAO userdao = new UserDAOImpl();
        User loggedInUser = userdao.getUserByCredentials(inputUsername, inputPassword);

        if (loggedInUser != null){
            switchScene("Dashboard");
            generateError("Cannot login at this moment.");
        } else{
            generateError("Invalid Credentials");
        }
    }

    @FXML
    public void signUp(ActionEvent event){
        switchScene("SignUp");
    }
}
