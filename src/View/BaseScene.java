package src.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import src.controller.Controller;

/**
 * The BaseScene class provides a base for creating JavaFX scenes with associated controllers.
 */
public abstract class BaseScene {
    protected Stage primaryStage;

    protected Scene scene;

	protected Controller controller;

    public BaseScene(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public abstract String getTitle();

    public abstract Scene getScene();

	public Controller getController(){
		return this.controller;
	}
	
    /**
     * Builds a JavaFX Scene by loading an FXML file and associating it with a controller.
     *
     * @param sceneFile The name of the FXML file representing the scene.
     * @return The constructed JavaFX Scene object.
     */
    public Scene buildScene(String sceneFile){
        
        if(scene != null) {
			return scene;
		}
		
		// load FXML
		FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("src/View/" + sceneFile));
				
		// load the FXML
		Parent parentNode = null;
		try {
			parentNode = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Controller controller = loader.getController();
		controller.setPrimaryStage(this.primaryStage);
		controller.init();
		this.controller = controller;
		
		// create a scene
		Scene scene = new Scene(parentNode);
		
		return scene;
    }
}
