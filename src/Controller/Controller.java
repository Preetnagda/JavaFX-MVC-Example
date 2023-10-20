package src.controller;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.view.BaseScene;
import src.view.SceneFactory;

/**
 * The Controller class is an abstract class that provides a base for other controller classes.
 * It contains common methods and properties for managing JavaFX scenes and handling errors and notifications.
 */
public abstract class Controller {
	protected Stage primaryStage;

    @FXML
    private Text error;

    /**
     * Sets the primary stage for the controller to manage scenes.
     *
     * @param primaryStage The JavaFX stage to set as the primary stage.
     */
    public void setPrimaryStage(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    /**
     * Switches to a new scene using the provided scene type and updates the primary stage.
     *
     * @param sceneType The type or identifier of the new scene.
     * @return The newly created BaseScene object for the switched scene.
     */
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

    /**
     * Sets an error or success notification message in the error Text element with the specified color.
     *
     * @param errorString      The notification message to display.
     * @param notificationType The type of notification ("success" or "error").
     */
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
    
    /**
     * An abstract method that can be implemented by subclasses to perform any additional initialization.
     */
    public void init(){}
}
