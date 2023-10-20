package src.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class RetrievePostScene extends BaseScene {

    public RetrievePostScene(Stage primaryStage) {
        super(primaryStage);
    }

    @Override
    public String getTitle() {
        return "Retrieve Post";
    }

    @Override
    public Scene getScene() {
        return buildScene("RetrievePost.fxml");
    }
    
}
