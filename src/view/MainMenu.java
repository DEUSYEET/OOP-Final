
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

public class MainMenu {

	private static VBox root = new VBox();
	private static Scene scene = new Scene(root, 600, 600);
	private static Stage mainStage;
	private static boolean isInited = false;

	public static Scene getScene(Stage whoIs) {

		if (!isInited) {
			initMainMenu(whoIs);
		}

		return scene;
	}

	public static Stage getStage() {
		return mainStage;
	}

	private static void initMainMenu(Stage whoIs) {

		mainStage = whoIs;
		root.setAlignment(Pos.CENTER);

		Button singlePlayer = new Button("Single Player");
		singlePlayer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				SpaceInvaders.gameRunning = true;
				if (SinglePlayer.isBeenRan()) {
					SinglePlayer.snap();
					SinglePlayer.setInited(false);
					SinglePlayer.addScore(-SinglePlayer.getScore());
					if (SinglePlayer.getLives().size()>0) {
						for (int i = 0; i < SinglePlayer.getLives().size(); i++) {
							SinglePlayer.removeLife();
						}
					}
						SpaceInvaders.gameRunning = true;
					
				}
				mainStage.setScene(SinglePlayer.getScene(whoIs));
			}

		});

		Button multiPlayer = new Button("MultiPlayer");
		multiPlayer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				mainStage.setScene(MultiPlayerMenu.getScene(whoIs));
			}

		});

		BackgroundFill background = new BackgroundFill(Color.BLACK, new CornerRadii(1), null);
		VBox optionsBox = new VBox(20);

		optionsBox.setAlignment(Pos.CENTER);
		optionsBox.setPadding(new Insets(20, 80, 20, 80));
		singlePlayer.setMinHeight(32);
		singlePlayer.setMinWidth(100);
		optionsBox.getChildren().add(singlePlayer);
		multiPlayer.setMinHeight(32);
		multiPlayer.setMinWidth(100);
		optionsBox.getChildren().add(multiPlayer);
		optionsBox.setBackground(new Background(background));
		optionsBox.setMinHeight(100000000);

		root.getChildren().addAll(optionsBox);

		isInited = true;

	}
}
