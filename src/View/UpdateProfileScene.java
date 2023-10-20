package src.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class UpdateProfileScene extends BaseScene{

    public UpdateProfileScene(Stage primaryStage) {
        super(primaryStage);
    }

    @Override
    public String getTitle() {
        return "Update Profile";
    }

    @Override
    public Scene getScene() {
        return buildScene("UpdateProfile.fxml");
    }
    
}
