
package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import controllers.CoOpControler;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;

public class PauseMenu {

	private static VBox root = new VBox();
	private static Scene scene = new Scene(root, 600, 600);
	private static Stage mainStage;
	private static boolean isInited = false;

	public static Scene getScene(Stage whoIs) {

		if (!isInited) {
			initPauseMenu(whoIs);
		}

		return scene;
	}

	public static Stage getStage() {
		return mainStage;
	}

	private static void initPauseMenu(Stage whoIs) {

		mainStage = whoIs;
		root.setAlignment(Pos.CENTER);

		Button continueGame = new Button("Continue");
		continueGame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
//				TODO

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
		Image image = new Image("assets/SpaceInvadersStart.jpg");
		BackgroundSize backgroundSize = new BackgroundSize(675, 600, false, false, false, false);
		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

		BackgroundFill buttons = new BackgroundFill(Color.TRANSPARENT, new CornerRadii(1), null);
		HBox optionsBox = new HBox(20);

		optionsBox.setAlignment(Pos.CENTER);
		optionsBox.setPadding(new Insets(500, 80, 20, 80));
		continueGame.setMinHeight(32);
		continueGame.setMinWidth(100);
		continueGame.setBackground(new Background(buttons));
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			continueGame.setFont(Font.loadFont(is, 30));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		continueGame.setTextFill(Color.WHITE);
		continueGame.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            continueGame.setEffect(shadow);
		            continueGame.setTextFill(Color.CYAN);
		          }
		        });
		continueGame.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            continueGame.setEffect(null);
		            continueGame.setTextFill(Color.WHITE);
		          }
		        });
		optionsBox.getChildren().add(continueGame);
		back.setMinHeight(32);
		back.setMinWidth(100);
		back.setBackground(new Background(buttons));
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			back.setFont(Font.loadFont(is, 30));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		back.setTextFill(Color.WHITE);
		back.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            back.setEffect(shadow);
		            back.setTextFill(Color.CYAN);
		          }
		 });
		back.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            back.setEffect(null);
		            back.setTextFill(Color.WHITE);
		          }
		        });
		optionsBox.getChildren().add(back);
		optionsBox.setBackground(new Background(backgroundImage));
		optionsBox.setMinHeight(100000000);

		root.getChildren().addAll(optionsBox);

		isInited = true;

	}
}