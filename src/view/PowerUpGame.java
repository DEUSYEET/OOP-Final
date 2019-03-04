package view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import application.Sprite;
import controllers.SpaceInvaders;
import javafx.animation.AnimationTimer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import models.Player;

public class PowerUpGame {
	
	private static VBox root = new VBox();
	private static Scene scene = new Scene(root, 600, 600);
	private static Stage mainStage;
	private static boolean isInited = false;
	private static Player player = new Player(1);
	private static Sprite playerSprite = player.getSprite();
	private static ArrayList<Sprite> enemies = new ArrayList<>();
	private static ArrayList<Sprite> shields = new ArrayList<>();
	private static VBox switchBox;
	private static int t;

	public static Scene getScene(Stage whoIs) {

		if (!isInited) {
			initStage(whoIs);
		}

		return scene;
	}
	
	

	public static VBox getRoot() {
		return root;
	}



	public static void setRoot(VBox root) {
		PowerUpGame.root = root;
	}



	public static Scene getScene() {
		return scene;
	}



	public static void setScene(Scene scene) {
		PowerUpGame.scene = scene;
	}



	public static Stage getMainStage() {
		return mainStage;
	}



	public static void setMainStage(Stage mainStage) {
		PowerUpGame.mainStage = mainStage;
	}



	public static boolean isInited() {
		return isInited;
	}



	public static void setInited(boolean isInited) {
		PowerUpGame.isInited = isInited;
	}



	public static Player getPlayer() {
		return player;
	}



	public static ArrayList<Sprite> getEnemies() {
		return enemies;
	}



	public static void setEnemies(ArrayList<Sprite> enemies) {
		PowerUpGame.enemies = enemies;
	}



	public static ArrayList<Sprite> getShields() {
		return shields;
	}



	public static void setShields(ArrayList<Sprite> shields) {
		PowerUpGame.shields = shields;
	}



	public static VBox getSwitchBox() {
		return switchBox;
	}



	public static void setSwitchBox(VBox switchBox) {
		PowerUpGame.switchBox = switchBox;
	}



	public static int getT() {
		return t;
	}



	public static void setT(int t) {
		PowerUpGame.t = t;
	}



	public static AnimationTimer getTimer() {
		return timer;
	}



	public static void setTimer(AnimationTimer timer) {
		PowerUpGame.timer = timer;
	}



	private static void initStage(Stage whoIs) {

		mainStage = whoIs;
		whoIs.setResizable(false);
		root.setAlignment(Pos.CENTER);
		VBox testRoot = new VBox();
		Scene scene = new Scene(testRoot, 600, 600);
		switchBox = new VBox();
		switchBox.setMinHeight(600);

		switchBox.getChildren().add(playerSprite);
		populateShields();
		populateEnemies();

		BackgroundFill back = new BackgroundFill(Color.BLACK, new CornerRadii(1), null);
		switchBox.setBackground(new Background(back));
		root.getChildren().addAll(switchBox);
		
		timer.start();
		isInited = true;
		
	}

	private static void populateShields() {
		int posX = 50;
		int posY = 400;

		for (int i = 0; i < 4; i++) {
			Sprite s = new Sprite(posX, posY - i * 64, "shield", "Shield", 64, 64, 1);
			shields.add(s);
			switchBox.getChildren().add(s);
			posX += 140;
		}

	}

	private static void populateEnemies() {
		int posX = 5;
		int posY = -200;
		int count = 0;
		String sprites[] = { "enemy1", "enemy2", "enemy3", "enemy4" };
		int file = 3;

		for (int i = 0; i < 40; i++) {
			if (count >= 10) {
				posY += 50;
				posX = 5;
				count = 0;
				file--;
			}
			Sprite s = new Sprite(posX, posY - i * 32, "enemy", sprites[file], 32, 32, 8);
			enemies.add(s);
			switchBox.getChildren().add(s);
			posX += 40;
			count++;
		}

	}
	
	
	private static List<Sprite> sprites() {
		return switchBox.getChildren().stream().map(n -> (Sprite) n).collect(Collectors.toList());
	}

	public static List<Sprite> getSprites() {
		return sprites();
	}
	
	

	private static AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
			SpaceInvaders.update();
		}
	};
}
