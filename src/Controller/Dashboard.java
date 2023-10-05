package src.Controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.Model.User;

public class Dashboard extends Application{

    private User user;

    @FXML
    private Text usernameGreeting;

    public Dashboard(User user){
        this.user = user;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("src/View/Dashboard.fxml"));
        primaryStage.setTitle("Analyzer App");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

}
