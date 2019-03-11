package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
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
		
		Button battle = new Button("Battle Mode");
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
		
		Button back = new Button("Return To Main Menu");
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				mainStage.setScene(MainMenu.getScene(whoIs));
			}

		});

		DropShadow shadow = new DropShadow();
		Image image = new Image("assets/side aliens.png");
		BackgroundSize backgroundSize = new BackgroundSize(675, 600, false, false, false, false);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		BackgroundFill buttons = new BackgroundFill(Color.TRANSPARENT, new CornerRadii(1), null);
		VBox optionsBox = new VBox();


		optionsBox.setAlignment(Pos.CENTER);
		optionsBox.setPadding(new Insets(20, 80, 20, 80));
		coOp.setMinHeight(32);
		coOp.setMinWidth(200);
		coOp.setBackground(new Background(buttons));
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			coOp.setFont(Font.loadFont(is, 40));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		coOp.setTextFill(Color.BLUE);
		coOp.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            coOp.setEffect(shadow);
		            coOp.setTextFill(Color.WHITE);
		          }
		        });
		coOp.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            coOp.setEffect(null);
		            coOp.setTextFill(Color.BLUE);
		          }
		        });
		optionsBox.getChildren().add(coOp);
		dlc.setMinHeight(32);
		dlc.setMinWidth(200);
		dlc.setBackground(new Background(buttons));
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			dlc.setFont(Font.loadFont(is, 40));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dlc.setTextFill(Color.RED);
		dlc.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            dlc.setEffect(shadow);
		            dlc.setTextFill(Color.WHITE);
		          }
		        });
		dlc.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            dlc.setEffect(null);
		            dlc.setTextFill(Color.RED);
		          }
		        });
		optionsBox.getChildren().add(dlc);
		battle.setMinHeight(32);
		battle.setMinWidth(200);
		battle.setBackground(new Background(buttons));
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			battle.setFont(Font.loadFont(is, 40));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		battle.setTextFill(Color.YELLOW);
		battle.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            battle.setEffect(shadow);
		            battle.setTextFill(Color.WHITE);
		          }
		        });
		battle.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            battle.setEffect(null);
		            battle.setTextFill(Color.YELLOW);
		          }
		        });
		optionsBox.getChildren().add(battle);
		back.setMinHeight(32);
		back.setMinWidth(200);
		back.setAlignment(Pos.CENTER);
		back.setBackground(new Background(buttons));
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			back.setFont(Font.loadFont(is, 40));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		back.setTextFill(Color.PURPLE);
		back.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            back.setEffect(shadow);
		            back.setTextFill(Color.WHITE);
		          }
		        });
		back.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            back.setEffect(null);
		            back.setTextFill(Color.PURPLE);
		          }
		        });
		optionsBox.getChildren().add(back);
		optionsBox.setBackground(new Background(backgroundImage));
		optionsBox.setMinHeight(100000000);

		root.getChildren().addAll(optionsBox);

		isInited = true;

	}
}
