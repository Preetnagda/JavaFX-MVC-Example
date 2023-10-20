package src.view;

import javafx.stage.Stage;

/**
 * The SceneFactory class is responsible for creating instances of requested scenes.
 */
public class SceneFactory {
    /**
     * Creates a scene based on the specified type and returns its instance.
     *
     * @param primaryStage The primary stage to associate with the created scene.
     * @param sceneType    The type or identifier of the scene to create.
     * @return The created BaseScene corresponding to the specified scene type.
     */
    public BaseScene create(Stage primaryStage, String sceneType){
        if(sceneType == "Login"){
            return new LoginScene(primaryStage);
        }
        if(sceneType == "SignUp"){
            return new SignUpScene(primaryStage);
        }
        if(sceneType == "Dashboard"){
            return new DashboardScene(primaryStage);
        }
        if(sceneType == "UpdateProfile"){
            return new UpdateProfileScene(primaryStage);
        }
        if(sceneType == "AddPost"){
            return new AddPostScene(primaryStage);
        }
        if(sceneType == "Analysis"){
            return new AnalysisScene(primaryStage);
        }
        // Default, return login scene
        return new LoginScene(primaryStage);
    }
}