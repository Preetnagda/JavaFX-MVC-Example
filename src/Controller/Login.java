package src.Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import src.Model.User;
import src.Model.DAO.UserDAO;
import src.Model.DAO.UserDAOImpl;

public class Login extends Application{

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/View/Login.fxml"));
        primaryStage.setTitle("Analyzer App");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    @FXML
    public void onLoginRequest(ActionEvent event){
        String inputUsername = username.getText();
        String inputPassword = password.getText();
        UserDAO userdao = new UserDAOImpl();
        User loggedInUser = userdao.getUserByCredentials(inputUsername, inputPassword);

        if (loggedInUser != null){
            System.out.println("Logged In");
        } else{
            System.out.println("Invalid Credentials");
        }
    }
}
