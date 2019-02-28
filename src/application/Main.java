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
	Sprite s = new Sprite(100, 100, 32, 32, "Player", "idle");
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

			timer.start();

		} catch (Exception e) {
			e.printStackTrace();
		}
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
