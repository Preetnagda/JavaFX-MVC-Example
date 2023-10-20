package src.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class AnalysisScene extends BaseScene{

    public AnalysisScene(Stage primaryStage) {
        super(primaryStage);
    }

    @Override
    public String getTitle() {
        return "Analysis Dashboard";
    }

    @Override
    public Scene getScene() {
        return buildScene("Analysis.fxml");
    }
    
}
