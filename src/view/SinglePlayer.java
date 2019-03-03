package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

import animations.Frame;
import application.*;

public class SinglePlayer {

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

	private static void initMainMenu(Stage whoIs) {

		mainStage = whoIs;
		root.setAlignment(Pos.CENTER);

		VBox testRoot = new VBox();
		Scene test = new Scene(testRoot, 600, 600);

		BackgroundFill back= new BackgroundFill(Color.BLACK, new CornerRadii(10), null);
		Sprite player = new Sprite(100, 100, "player", "idle", 32, 32, 8);

		VBox switchBox = new VBox();
		switchBox.setAlignment(Pos.CENTER);
		switchBox.setPadding(new Insets(20, 80, 20, 80));
		switchBox.getChildren().add(player);
		switchBox.setBackground(new Background(back));
		root.getChildren().addAll(switchBox);

		isInited = true;

	}
}
