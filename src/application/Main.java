
package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import view.MainMenu;

public class Main extends Application {

	BorderPane root;

	@Override
	public void start(Stage primaryStage) {
		try {
			
			Scene mainMenu = MainMenu.getScene(primaryStage);
			primaryStage.setScene(mainMenu);

			primaryStage.setResizable(true);
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
