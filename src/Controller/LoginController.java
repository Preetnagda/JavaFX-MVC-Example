package src.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import src.view.BaseScene;
import src.model.AuthUser;
import src.model.dao.UserDAO;
import src.model.dao.UserDAOImpl;

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
        AuthUser loggedInUser = userdao.getUserByCredentials(inputUsername, inputPassword);

        if (loggedInUser != null){
            BaseScene newScene = switchScene("Dashboard");
            LoggedInController newController = (LoggedInController) newScene.getController();
            newController.setUser(loggedInUser);
            newController.initWithUser();
        } else{
            generateError("Invalid Credentials");
        }
    }

    @FXML
    public void signUp(ActionEvent event){
        switchScene("SignUp");
    }
}
