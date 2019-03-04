
package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.MainMenu;

public class Main extends Application {

	BorderPane root;

	@Override
	public void start(Stage primaryStage) {
		try {
			
			Scene mainMenu = MainMenu.getScene(primaryStage);
			primaryStage.setTitle("Space Invaders");
			primaryStage.setScene(mainMenu);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Space Invaders Battle");
			primaryStage.show();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
