package src;

import javafx.application.Application;
import javafx.stage.Stage;
import src.view.LoginScene;

public class Main extends Application{

    @Override
	public void start(Stage primaryStage) throws Exception {
	
		LoginScene imageViewerScene = new LoginScene(primaryStage);
		primaryStage.setResizable(false);
		primaryStage.setTitle(imageViewerScene.getTitle());
		primaryStage.setScene(imageViewerScene.getScene());
		
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}