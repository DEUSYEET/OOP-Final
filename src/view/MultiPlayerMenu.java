package view;

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

public class MultiPlayerMenu {

	private static VBox root = new VBox();
	private static Scene scene = new Scene(root, 600, 600);
	private static Stage mainStage;
	private static boolean isInited = false;

	public static Scene getScene(Stage whoIs) {

		if (!isInited) {
			initMultiPlayerMenu(whoIs);
		}

		return scene;
	}

	private static void initMultiPlayerMenu(Stage whoIs) {

		mainStage = whoIs;
		root.setAlignment(Pos.CENTER);

		Button coOp = new Button("Co-Op Space Invaders");
		coOp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				mainStage.setScene(CoOpGame.getScene(whoIs));
			}

		});
		
		Button powerUp = new Button("Powered-Up Invaders");
		powerUp.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				mainStage.setScene(PowerUpGame.getScene(whoIs));
			}

		});
		
		Button battle = new Button("Battle");
		battle.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				mainStage.setScene(BattleGame.getScene(whoIs));
			}

		});
		
		Button dlc = new Button("Dlc Co-Op");
		dlc.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				mainStage.setScene(PowerUpGame.getScene(whoIs));
			}

		});
		
		Button competitive = new Button("Competitive");
		competitive.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				mainStage.setScene(PowerUpGame.getScene(whoIs));
			}

		});

		BackgroundFill background = new BackgroundFill(Color.BLACK, new CornerRadii(1), null);
		VBox optionsBox = new VBox(20);

		optionsBox.setAlignment(Pos.CENTER);
		optionsBox.setPadding(new Insets(20, 80, 20, 80));
		competitive.setMinHeight(32);
		competitive.setMinWidth(200);
		optionsBox.getChildren().add(competitive);
		coOp.setMinHeight(32);
		coOp.setMinWidth(200);
		optionsBox.getChildren().add(coOp);
		dlc.setMinHeight(32);
		dlc.setMinWidth(200);
		optionsBox.getChildren().add(dlc);
		powerUp.setMinHeight(32);
		powerUp.setMinWidth(200);
		optionsBox.getChildren().add(powerUp);
		battle.setMinHeight(32);
		battle.setMinWidth(200);
		optionsBox.getChildren().add(battle);
		optionsBox.setBackground(new Background(background));
		optionsBox.setMinHeight(100000000);

		root.getChildren().addAll(optionsBox);

		isInited = true;

	}
}
