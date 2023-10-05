package src.Controller;

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
import src.Model.User;
import src.Model.DAO.UserDAO;
import src.Model.DAO.UserDAOImpl;

public class Login extends Application{

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Text error;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/View/Login.fxml"));
        primaryStage.setTitle("Analyzer App");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

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
            try {
                final Node source = (Node) event.getSource();
                final Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                new Dashboard(loggedInUser).start(new Stage());
            } catch (Exception e) {
                generateError("Cannot login at this moment.");
            }
        } else{
            generateError("Invalid Credentials");
        }
    }

    @FXML
    public void signUp(ActionEvent event){
        try {
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

            new SignUp().start(new Stage());
        } catch (Exception e) {
            generateError("Cannot sign up at this moment.");
        }
    }

    private void generateError(String errorString){
        error.setText(errorString);
    }
}
