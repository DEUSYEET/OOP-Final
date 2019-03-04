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

public class MainMenu {
	
	private static VBox root = new VBox();
	private static Scene scene = new Scene(root , 600, 600);
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
		
		Button singlePlayer = new Button("Single Player");
		singlePlayer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				mainStage.setScene(SinglePlayer.getScene(whoIs));
			}
			
		});
		
		BackgroundFill background = new BackgroundFill(Color.BLACK, new CornerRadii(1), null);
		VBox optionsBox = new VBox();
	
		optionsBox.setAlignment(Pos.CENTER);
		optionsBox.setPadding(new Insets(20, 80, 20, 80));
		optionsBox.getChildren().add(singlePlayer);
		optionsBox.setBackground(new Background(background));
		optionsBox.setMinHeight(100000000);
		
		root.getChildren().addAll(optionsBox);
		
		
		isInited = true;
		
	}
}
