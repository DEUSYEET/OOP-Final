package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Player;

public class SinglePlayer {
	
	private static boolean beenRan = false;
	private static VBox root = new VBox();
	private static Scene scene = new Scene(root, 600, 600);
	private static Stage mainStage;
	private static boolean isInited = false;
	private static Player player = new Player(1);
	private static Sprite playerSprite = player.getSprite();
	private static ArrayList<Sprite> enemies = new ArrayList<>();
	private static ArrayList<Sprite> shields = new ArrayList<>();
	private static ArrayList<Sprite> lives = new ArrayList<>();
	private static VBox switchBox;
	private static VBox scoreBox;
	private static int t;
	private static int score = 0;
	
	private static Text scoreText = new Text(0, 0, "Score: " + Integer.toString(score));

	public static Scene getScene(Stage whoIs) {
		
		setBeenRan(true);
		
		if (!isInited) {
			initStage(whoIs);
		}

		return scene;
	}

	public static VBox getRoot() {
		return root;
	}

	public static void setRoot(VBox root) {
		SinglePlayer.root = root;
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		SinglePlayer.scene = scene;
	}

	public static Stage getMainStage() {
		return mainStage;
	}

	public static void setMainStage(Stage mainStage) {
		SinglePlayer.mainStage = mainStage;
	}

	public static boolean isInited() {
		return isInited;
	}

	public static void setInited(boolean isInited) {
		SinglePlayer.isInited = isInited;
	}

	public static Player getPlayer() {
		return player;
	}

	public static ArrayList<Sprite> getEnemies() {
		return enemies;
	}

	public static void setEnemies(ArrayList<Sprite> enemies) {
		SinglePlayer.enemies = enemies;
	}

	public static ArrayList<Sprite> getShields() {
		return shields;
	}

	public static void setShields(ArrayList<Sprite> shields) {
		SinglePlayer.shields = shields;
	}

	public static VBox getSwitchBox() {
		return switchBox;
	}

	public static void setSwitchBox(VBox switchBox) {
		SinglePlayer.switchBox = switchBox;
	}

	public static int getT() {
		return t;
	}

	public static void setT(int t) {
		SinglePlayer.t = t;
	}

	public static AnimationTimer getTimer() {
		return timer;
	}

	public static void setTimer(AnimationTimer timer) {
		SinglePlayer.timer = timer;
	}

	
	public static void addScore(int addScore) {
		score += addScore;
		scoreText.setText("Score: "+Integer.toString(score));
	}
	public static int getScore() {
		return score;
	}


	public static void initStage(Stage whoIs) {
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

		scoreBox = new VBox();
		scoreBox.setMinHeight(50);
		scoreBox.getChildren().add(scoreText);
		scoreText.setFill(Color.WHITE);
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			scoreText.setFont(Font.loadFont(is, 23));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scoreBox.setBackground(new Background(back));
		populateLives();

		root.getChildren().add(scoreBox);

		timer.start();
		isInited = true;

	}

	private static void populateShields() {
		int posX = 50;
		int posY = 400;

		for (int i = 0; i < 4; i++) {
			Sprite s = new Sprite(posX, posY - i * 41, "shield", "Shield", 64, 41, 10);
			shields.add(s);
			switchBox.getChildren().add(s);
			posX += 140;
		}

	}
	
	private static void populateLives() {
		int posX = 400;
		int posY = -23;

		for (int i = 0; i < player.getLives(); i++) {
			Sprite s = new Sprite(posX, posY - i * 24, "live", "idle", 32, 24, 1);
			lives.add(s);
			scoreBox.getChildren().add(lives.get(i));
			posX += 40;
		}

	}

	public static void populateEnemies() {
		int posX = 5;
		int posY = -160;
		int count = 0;
		String sprites[] = { "enemy1", "enemy2", "enemy3", "enemy4" };
		int X[] = { 20, 24, 18, 28 };
//		int Y[] = {22,25,25,22};
		int file = 3;

		for (int i = 0; i < 40; i++) {
			if (count >= 10) {
				posY += 50;
				posX = 5;
				count = 0;
				file--;
			}
			Sprite s = new Sprite(posX, posY - i * 32, "enemy", sprites[file], X[file], 32, 8);
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

	public static ArrayList<Sprite> getLives() {
		return lives;
	}

	public static void setLives(ArrayList<Sprite> lives) {
		SinglePlayer.lives = lives;
	}
	public static void removeLife() {
		scoreBox.getChildren().remove(lives.remove(lives.size()-1));
	}
	
	private static AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
				SpaceInvaders.update();
		}
	};

	public static void snap() {
		//perfectly balanced, as all things should be
		int perserve = SpaceInvaders.timesSnapped;
		root.getChildren().removeAll(root.getChildren());
		SpaceInvaders.timesSnapped = perserve;
	}

	public static boolean isBeenRan() {
		return beenRan;
	}

	public static void setBeenRan(boolean beenRan) {
		SinglePlayer.beenRan = beenRan;
	}


}
