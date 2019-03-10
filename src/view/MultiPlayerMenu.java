package view;

import controllers.BattleController;
import controllers.CoOPDLCControler;
import controllers.CoOpControler;
import controllers.CompetetiveController;
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
				CoOpControler newGame = new CoOpControler();
				newGame.stage = whoIs;
				newGame.start();
				mainStage.setScene(newGame.getLevel().getScene());

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
				BattleController.gameRunning = true;
				if (BattleGame.isBeenRan()) {
					BattleController.setEnemySpeed(100);
					BattleGame.snap();
					BattleGame.setInited(false);
					if (BattleGame.getLives(1).size()>0) {
						for (int i = 0; i < BattleGame.getLives(1).size(); i++) {
							BattleGame.removeLife(1);
						}
					}
						BattleController.gameRunning = true;
					
				}
				mainStage.setScene(BattleGame.getScene(whoIs));
			}
		});
		
		Button dlc = new Button("Teletubbies Co-Op");
		dlc.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				CoOPDLCControler newGame = new CoOPDLCControler();
				newGame.stage = whoIs;
				newGame.start();
				mainStage.setScene(newGame.getLevel().getScene());

			}

		});
		
		Button competitive = new Button("Competitive");
		competitive.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				CompetetiveController.gameRunning = true;
				if (CompetetiveGame.isBeenRan()) {
					CompetetiveController.setEnemySpeed(100);
					CompetetiveGame.snap();
					CompetetiveGame.setInited(false);
					if (CompetetiveGame.getLives(1).size()>0) {
						for (int i = 0; i <CompetetiveGame.getLives(1).size(); i++) {
							CompetetiveGame.removeLife(1);
						}
					}
					CompetetiveController.gameRunning = true;
					
				}
				mainStage.setScene(CompetetiveGame.getScene(whoIs));
			}

		});
		Button back = new Button("Return To Main Menu");
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				mainStage.setScene(MainMenu.getScene(whoIs));
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
		back.setMinHeight(32);
		back.setMinWidth(200);
		optionsBox.getChildren().add(back);
		optionsBox.setBackground(new Background(background));
		optionsBox.setMinHeight(100000000);

		root.getChildren().addAll(optionsBox);

		isInited = true;

	}
}
