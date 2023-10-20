package src.view;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SignUpScene extends BaseScene {

    public SignUpScene(Stage primaryStage) {
        super(primaryStage);
    }

    public String getTitle(){
        return "SignUp";
    }

    public Scene getScene(){
        return buildScene("SignUp.fxml");
    }

}
