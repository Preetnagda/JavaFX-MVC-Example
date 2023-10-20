package src.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class DashboardScene extends BaseScene {
    
    public DashboardScene(Stage primaryStage) {
        super(primaryStage);
    }

    public String getTitle(){
        return "Dashboard";
    }

    public Scene getScene(){
        return buildScene("Dashboard.fxml");
    }

}
