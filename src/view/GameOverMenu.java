package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import application.Sprite;
import controllers.SpaceInvaders;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameOverMenu {

	private static VBox root = new VBox();
	private static Scene scene = new Scene(root, 600, 600);
	private static Stage mainStage;
	private static boolean isInited = false;

	public static Scene getScene(Stage whoIs) {

		
		if (!isInited) {
			initGameOverMenu(whoIs);
		}

		return scene;
	}

	public static Stage getStage() {
		return mainStage;
	}


	private static void initGameOverMenu(Stage whoIs) {
		mainStage = whoIs;
		root.setAlignment(Pos.CENTER);

		Button playAgain = new Button("Play Again");
		playAgain.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				SpaceInvaders newGame = new SpaceInvaders();
				newGame.stage = whoIs;
				newGame.start();
				mainStage.setScene(newGame.getLevel().getScene());
			}

		});

		Button mainMenu = new Button("Main Menu");
		mainMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				mainStage.setScene(MainMenu.getScene(whoIs));
			}

			
			
			
		});

		Image image = new Image("assets/SpaceInvadersGameOver.png");
		BackgroundSize backgroundSize = new BackgroundSize(675, 600, false, false, false, false);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		
		BackgroundFill buttons = new BackgroundFill(Color.TRANSPARENT, new CornerRadii(1), null);
		HBox optionsBox = new HBox(20);

		optionsBox.setAlignment(Pos.CENTER);
		optionsBox.setPadding(new Insets(525, 80, 20, 80));
		playAgain.setMinHeight(32);
		playAgain.setMinWidth(100);
		playAgain.setBackground(new Background(buttons));
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			playAgain.setFont(Font.loadFont(is, 30));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		playAgain.setTextFill(Color.WHITE);
		optionsBox.getChildren().add(playAgain);
		mainMenu.setMinHeight(32);
		mainMenu.setMinWidth(100);
		mainMenu.setBackground(new Background(buttons));
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			mainMenu.setFont(Font.loadFont(is, 30));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mainMenu.setTextFill(Color.WHITE);
		optionsBox.getChildren().add(mainMenu);
		optionsBox.setBackground(new Background(backgroundImage));
		optionsBox.setMinHeight(100000000);

		root.getChildren().addAll(optionsBox);

		isInited = true;

	}
}
