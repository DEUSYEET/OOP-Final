package application;

import assets.Animation;
import assets.Frame;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	Animation a = new Animation("idle");
	Animation clear = new Animation("clear");
	double t = 0;
	GraphicsContext gc;
	//ImageView img = new ImageView(a.getCurrentFrame());

	@Override
	public void start(Stage primaryStage) {
		try {

			BorderPane root = new BorderPane();
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			Canvas c = new Canvas(400, 400);
			root.getChildren().add(c);
			
		gc = c.getGraphicsContext2D();

			
			
			
			
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
		t += 0.016;
		gc.clearRect(0, 0, 400, 400);
		gc.drawImage(a.getCurrentFrame(), 10, 10);
		System.out.println(t);
		if (t>.1) {
		a.nextFrame();
		t =0;
		}
	}

	AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
			update();
		}
	};
}
