package src.Controller;

import java.sql.SQLException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.CustomExceptions.DuplicateUser;
import src.Model.User;
import src.Model.DAO.UserDAO;
import src.Model.DAO.UserDAOImpl;

public class SignUp extends Application{

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
    private Text error;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/View/SignUp.fxml"));
        primaryStage.setTitle("Analyzer App");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

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

        } catch (DuplicateUser e) {
            generateError("Username already exists, please try a different username");
        } catch (SQLException e){
            generateError("User cannot be created at this time, please try again later");
        }
    }

    @FXML
    public void login(ActionEvent event){
        try {
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

            new Login().start(new Stage());
        } catch (Exception e) {
            generateError("Cannot sign up at this moment.");
        }
    }

    private void generateError(String errorString){
        error.setText(errorString);
    }
}
