package src.View;

import javafx.stage.Stage;

public class SceneFactory {
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
        // Default, return login scene
        return new LoginScene(primaryStage);
    }
}