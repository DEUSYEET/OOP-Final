package application;

import assets.Animation;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	Animation a = new Animation("fire");
	double t = 0;
	GraphicsContext gc;
	// ImageView img = new ImageView(a.getCurrentFrame());

	@Override
	public void start(Stage primaryStage) {
		try {

			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			Canvas c = new Canvas(400, 400);
			root.getChildren().add(c);

			gc = c.getGraphicsContext2D();

			int i2 = 0;
			for (Image i : a.getFrames()) {
				gc.drawImage(i, 32*i2, 50);
				i2++;
			}
			
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

	private void update() {
		t += 1;
		System.out.println(t);
		if (t > 10) {
			a.nextFrame();
			gc.clearRect(0, 0,45,45);
			gc.drawImage(a.getCurrentFrame(), 10, 10);
			t = 0;
		}
	}

	AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
			update();
		}
	};
}
