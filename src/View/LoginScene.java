package src.view;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginScene extends BaseScene {

    public LoginScene(Stage primaryStage) {
        super(primaryStage);
    }

    public String getTitle(){
        return "Login";
    }

    public Scene getScene(){
        return buildScene("Login.fxml");
    }

}
