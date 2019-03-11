
package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

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
				SpaceInvaders newGame = new SpaceInvaders();
				newGame.stage = whoIs;
				newGame.start();
				mainStage.setScene(newGame.getLevel().getScene());
			}

		});

		Button multiPlayer = new Button("MultiPlayer");
		multiPlayer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				mainStage.setScene(MultiPlayerMenu.getScene(whoIs));
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
		singlePlayer.setMinHeight(32);
		singlePlayer.setMinWidth(100);
		singlePlayer.setBackground(new Background(buttons));
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			singlePlayer.setFont(Font.loadFont(is, 30));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		singlePlayer.setTextFill(Color.WHITE);
		singlePlayer.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            singlePlayer.setEffect(shadow);
		            singlePlayer.setTextFill(Color.CYAN);
		          }
		        });
		singlePlayer.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            singlePlayer.setEffect(null);
		            singlePlayer.setTextFill(Color.WHITE);
		          }
		        });
		optionsBox.getChildren().add(singlePlayer);
		multiPlayer.setMinHeight(32);
		multiPlayer.setMinWidth(100);
		multiPlayer.setBackground(new Background(buttons));
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			multiPlayer.setFont(Font.loadFont(is, 30));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		multiPlayer.setTextFill(Color.WHITE);
		multiPlayer.addEventHandler(MouseEvent.MOUSE_ENTERED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            multiPlayer.setEffect(shadow);
		            multiPlayer.setTextFill(Color.CYAN);
		          }
		 });
		multiPlayer.addEventHandler(MouseEvent.MOUSE_EXITED,
		        new EventHandler<MouseEvent>() {
		          @Override
		          public void handle(MouseEvent e) {
		            multiPlayer.setEffect(null);
		            multiPlayer.setTextFill(Color.WHITE);
		          }
		        });
		optionsBox.getChildren().add(multiPlayer);
		optionsBox.setBackground(new Background(backgroundImage));
		optionsBox.setMinHeight(100000000);

		root.getChildren().addAll(optionsBox);

		isInited = true;

	}
}