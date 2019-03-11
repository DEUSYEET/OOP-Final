
package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import controllers.CoOPDLCControler;
import controllers.SpaceInvaders;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
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
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DLCOver {

	private static VBox root = new VBox();
	private static Scene scene = new Scene(root, 600, 600);
	private static Stage mainStage;
	private static boolean isInited = false;
	private static AudioClip deathSound = new AudioClip("file:src/assets/death.mp3");

	public static Scene getScene(Stage whoIs) {
		deathSound.play(1);
		if (!isInited) {
			initGameOverMenu(whoIs);
		}

		return scene;
	}

	public static Stage getStage() {
		return mainStage;
	}

	private static String playAgainText = "Play Again";
	private static String mainMenuText = "Main Menu";

	public static String getPlayAgainText() {
		return playAgainText;
	}

	public static String getMainMenuText() {
		return mainMenuText;
	}

	private static void initGameOverMenu(Stage whoIs) {
		mainStage = whoIs;
		root.setAlignment(Pos.CENTER);


		Button playAgain = new Button(getPlayAgainText());
		playAgain.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				CoOPDLCControler newGame = new CoOPDLCControler();
				newGame.stage = whoIs;
				newGame.start();
				mainStage.setScene(newGame.getLevel().getScene());
				deathSound.stop();
			}

		});

		Button mainMenu = new Button(getMainMenuText());
		mainMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				deathSound.stop();
				mainStage.setScene(MainMenu.getScene(whoIs));
			}

		});
		Image img = new Image("assets/gameover.jpg");
		BackgroundImage background = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(10, 50, true, true, true, false));
		BackgroundFill buttons = new BackgroundFill(Color.BLACK, new CornerRadii(1), null);
		VBox optionsBox = new VBox(20);
		optionsBox.setBackground(new Background(background));
		optionsBox.setMinHeight(600);
		optionsBox.setAlignment(Pos.BOTTOM_CENTER);
		optionsBox.setPadding(new Insets(125, 80, 125, 80));
		try {
			InputStream is = new FileInputStream("src/assets/OptimusPrincepsSemiBold.ttf");
			playAgain.setFont(Font.loadFont(is, 23));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		playAgain.setBackground(new Background(buttons));
		playAgain.setTextFill(Color.DARKRED);
		playAgain.setMinHeight(32);
		playAgain.setMinWidth(100);
		optionsBox.getChildren().add(playAgain);
		try {
			InputStream is = new FileInputStream("src/assets/OptimusPrincepsSemiBold.ttf");
			mainMenu.setFont(Font.loadFont(is, 23));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		mainMenu.setBackground(new Background(buttons));
		mainMenu.setTextFill(Color.DARKRED);
		mainMenu.setMinHeight(32);
		mainMenu.setMinWidth(100);
		optionsBox.getChildren().add(mainMenu);

		root.getChildren().addAll(optionsBox);

		isInited = true;

	}
}