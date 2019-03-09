
package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.MainMenu;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			MainMenu mainMenu = new MainMenu(primaryStage);
			
			primaryStage.setScene(mainMenu.getScene());
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
