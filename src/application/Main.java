package application;

import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	BorderPane root;
	Sprite s = new Sprite(100, 100, "Player", "idle");
	double t = 0;

	@Override
	public void start(Stage primaryStage) {
		try {

			root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
		
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			root.getChildren().add(s);

			primaryStage.setScene(scene);
			primaryStage.show();
			
			controls(scene);
			
			
			

			timer.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void controls(Scene scene) {
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case A:
				s.moveLeft();
				break;
			case D:
				s.moveRight();
				break;
			case SPACE:
				break;
			default:
				break;
			}
		});		
	}

	public static void main(String[] args) {
		launch(args);
	}

	private List<Sprite> sprites() {
		return root.getChildren().stream().map(n -> (Sprite) n).collect(Collectors.toList());
	}

	private void update() {
		t++;
		if (t > 2) {
			for (Sprite s : sprites()) {
				s.update();
			}
			
			t=0;
		}

	}

	AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
			update();
		}
	};
}
