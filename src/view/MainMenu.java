
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
	
	private VBox root;
	private Scene mainMenu;
	private Button singlePlayer = new Button("Single Player");
	private Button multiPlayer = new Button("MultiPlayer");
	private Stage inside;
	
	public MainMenu(Stage inside) {
		
		root = new VBox();
		mainMenu = new Scene(root, 600,600);
		this.inside = inside;
		
		initMainMenu();
		
	}
	
	public Scene getScene() {
		return mainMenu;
	}

	private void initMainMenu() {
		
		BackgroundFill background = new BackgroundFill(Color.BLACK, new CornerRadii(1), null);
		SinglePlayer sInglePlayer = new SinglePlayer();
		
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(20, 80, 20, 80));
		singlePlayer.setMinHeight(32);
		singlePlayer.setMinWidth(200);
		singlePlayer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				inside.setScene(sInglePlayer.getScene());
				
			}

		});
		multiPlayer.setMinHeight(32);
		multiPlayer.setMinWidth(200);
		root.setBackground(new Background(background));
		
		root.getChildren().add(singlePlayer);
		root.getChildren().add(multiPlayer);
		root.setBackground(new Background(background));
		
	}
	
}
