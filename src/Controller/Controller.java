package src.controller;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.view.BaseScene;
import src.view.SceneFactory;

public abstract class Controller {
	protected Stage primaryStage;

    @FXML
    private Text error;

    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public BaseScene switchScene(String sceneType){
        SceneFactory sceneFactory = new SceneFactory();
        BaseScene newScene = sceneFactory.create(primaryStage, sceneType);

        primaryStage.setTitle(newScene.getTitle());
		primaryStage.setScene(newScene.getScene());

		primaryStage.show();
        return newScene;
    }

    protected void generateError(String errorString){
        error.setText(errorString);
        error.setFill(Color.RED);
    }

    protected void generateNotification(String errorString, String notificationType){
        error.setText(errorString);
        if(notificationType.equals("success")){
            error.setFill(Color.GREEN);
        }
        if(notificationType.equals("error")){
            error.setFill(Color.RED);
        }
    }

    protected void clearError(){
        error.setText("");
        error.setFill(Color.RED);
    }

    public void init(){}
}
