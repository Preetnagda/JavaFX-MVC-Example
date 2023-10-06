package src.Controller;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.View.BaseScene;
import src.View.SceneFactory;

public abstract class Controller {
	protected Stage primaryStage;

    @FXML
    private Text error;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void switchScene(String sceneType){
        SceneFactory sceneFactory = new SceneFactory();
        BaseScene newScene = sceneFactory.create(primaryStage, sceneType);

        primaryStage.setTitle(newScene.getTitle());
		primaryStage.setScene(newScene.getScene());

		primaryStage.show();
    }

    protected void generateError(String errorString){
        error.setText(errorString);
    }
}
