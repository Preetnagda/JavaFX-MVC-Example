package src.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddPostScene extends BaseScene {

    public AddPostScene(Stage primaryStage) {
        super(primaryStage);
    }

    @Override
    public String getTitle() {
        return "Add Post";
    }

    @Override
    public Scene getScene() {
        return buildScene("AddPost.fxml");
    }
    
}
