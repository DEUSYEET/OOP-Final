package view;

import controllers.SpaceInvaders;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
				SinglePlayer.addScore(-SinglePlayer.getScore());
				SpaceInvaders.gameRunning = true;
				//SinglePlayer.snap();
				SinglePlayer.setInited(false);
				mainStage.setScene(SinglePlayer.getScene(whoIs));
				
			}

		});

		Button mainMenu = new Button("Main Menu");
		mainMenu.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				mainStage.setScene(MainMenu.getScene(whoIs));
			}

			
			
			
		});

		BackgroundFill background = new BackgroundFill(Color.BLACK, new CornerRadii(1), null);
		VBox optionsBox = new VBox(20);

		optionsBox.setAlignment(Pos.CENTER);
		optionsBox.setPadding(new Insets(20, 80, 20, 80));
		playAgain.setMinHeight(32);
		playAgain.setMinWidth(100);
		optionsBox.getChildren().add(playAgain);
		mainMenu.setMinHeight(32);
		mainMenu.setMinWidth(100);
		optionsBox.getChildren().add(mainMenu);
		optionsBox.setBackground(new Background(background));
		optionsBox.setMinHeight(100000000);

		root.getChildren().addAll(optionsBox);

		isInited = true;

	}
}
