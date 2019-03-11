package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import application.Sprite;
import controllers.BattleController;
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

public class BattleGame {
	
	private static Player player2 = new Player(2);
	private static boolean beenRan = false;
	private static VBox root = new VBox();
	private static Scene scene = new Scene(root, 600, 900);
	private static Stage mainStage;
	private static boolean isInited = false;
	private static Player player1 = new Player(1);
	private static Sprite player1Sprite = player1.getSprite();
	private static Sprite player2Sprite = player2.getSprite();
	private static ArrayList<Sprite> enemies = new ArrayList<>();
	private static ArrayList<Sprite> shields = new ArrayList<>();
	private static ArrayList<Sprite> lives = new ArrayList<>();
	private static ArrayList<Sprite> lives2 = new ArrayList<>();
	private static VBox switchBox;
	private static VBox scoreBox;
	private static VBox scoreBox2;
	private static int t;
	private static int score = 0;
	private static int score2 = 0;
	private static int livesCount;
	private static int livesCount2;
	private static Text scoreText = new Text(0, 0, "Score: " + Integer.toString(score));
	private static Text scoreText2 = new Text(0, 0, "Score: " + Integer.toString(score2));

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
		BattleGame.root = root;
	}

	public static Scene getScene() {
		return scene;
	}

	public static void setScene(Scene scene) {
		BattleGame.scene = scene;
	}

	public static Stage getMainStage() {
		return mainStage;
	}

	public static void setMainStage(Stage mainStage) {
		BattleGame.mainStage = mainStage;
	}

	public static boolean isInited() {
		return isInited;
	}

	public static void setInited(boolean isInited) {
		BattleGame.isInited = isInited;
	}

	public static Player getPlayer(int player) {
		if (player ==1) {
		return player1;}
		else {
			return player2;
		}
	}

	public static ArrayList<Sprite> getEnemies() {
		return enemies;
	}

	public static void setEnemies(ArrayList<Sprite> enemies) {
		BattleGame.enemies = enemies;
	}

	public static ArrayList<Sprite> getShields() {
		return shields;
	}

	public static void setShields(ArrayList<Sprite> shields) {
		BattleGame.shields = shields;
	}

	public static VBox getSwitchBox() {
		return switchBox;
	}

	public static void setSwitchBox(VBox switchBox) {
		BattleGame.switchBox = switchBox;
	}

	public static int getT() {
		return t;
	}

	public static void setT(int t) {
		BattleGame.t = t;
	}

	public static AnimationTimer getTimer() {
		return timer;
	}

	public static void setTimer(AnimationTimer timer) {
		BattleGame.timer = timer;
	}

	
	public static void addScore(int addScore, int player) {
		System.out.println("Player");
		if (player==1) {
		score += addScore;
		scoreText.setText("Score: "+Integer.toString(score));
		} else {
			score2 += addScore;
			scoreText2.setText("Score: "+Integer.toString(score2));
		}
	}
	public static int getScore(int player) {
		if (player ==1) {
			
		return score;
		}else {
			return score2;
		}
	}


	public static void initStage(Stage whoIs) {
		BackgroundFill back = new BackgroundFill(Color.BLACK, new CornerRadii(1), null);
		mainStage = whoIs;
		whoIs.setResizable(false);
		root.setAlignment(Pos.CENTER);
		VBox testRoot = new VBox();
		Scene scene = new Scene(testRoot, 600, 1100);
		switchBox = new VBox();
		switchBox.setMinHeight(600);
		switchBox.setMaxHeight(1200);

		scoreBox2 = new VBox();
		scoreBox2.setMinHeight(50);
		scoreBox2.getChildren().add(scoreText2);
		scoreText2.setFill(Color.WHITE);
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			scoreText2.setFont(Font.loadFont(is, 23));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		scoreBox2.setBackground(new Background(back));
		
		root.getChildren().add(scoreBox2);
		
		
		
		switchBox.getChildren().add(player1Sprite);
		switchBox.getChildren().add(player2Sprite);
		player1Sprite.setTranslateY(730);
		player2Sprite.setTranslateY(-50);
		populateShields();
		populateEnemies();

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
		int posY = 600;

		for (int i = 0; i < 4; i++) {
			Sprite s = new Sprite(posX, posY - i * 41, "shield", "Shield", 64, 41, 10);
			shields.add(s);
			switchBox.getChildren().add(s);
			posX += 140;
		}
		posX=50;
		posY=-170;
		for (int i = 0; i < 4; i++) {
			Sprite s = new Sprite(posX, posY - i * 41, "shield", "Shield2", 64, 41, 10);
			shields.add(s);
			switchBox.getChildren().add(s);
			posX += 140;
		}

	}
	
	private static void populateLives() {
		int posX = 400;
		int posY = -23;

		for (int i = 0; i < player1.getLives(); i++) {
			Sprite s = new Sprite(posX, posY - i * 24, "live", "idle1", 32, 24, 1);
			lives.add(s);
			scoreBox.getChildren().add(lives.get(i));
			posX += 40;
			livesCount++;
		}
		 posX = 400;
		 posY = -23;
		for (int i = 0; i < player1.getLives(); i++) {
			Sprite s = new Sprite(posX, posY - i * 24, "live", "idle2", 32, 24, 1);
			lives2.add(s);
			scoreBox2.getChildren().add(lives2.get(i));
			posX += 40;
			livesCount2++;
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

		for (int i = 0; i < 60; i++) {
			if (file<1) {
				file = 3;
			}
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

	public static ArrayList<Sprite> getLives(int player) {
		if (player==1) {
		return lives;
		} else {
			return lives2;
		}
	}

	public static void setLives(ArrayList<Sprite> lives, int player) {
		if (player==1) {
			BattleGame.lives = lives;
			} else {
				BattleGame.lives2 = lives;
			}
	}
	public static void removeLife(int player) {
		if (player ==1 ) {
		scoreBox.getChildren().remove(lives.remove(lives.size()-1));
		livesCount--;
		} else {
			scoreBox2.getChildren().remove(lives2.remove(lives2.size()-1));
			livesCount2--;
		}
	}
	
	private static AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
				BattleController.update();
		}
	};

	public static void snap() {
		//perfectly balanced, as all things should be
			root.getChildren().removeAll(root.getChildren());

			//score = 0;
			if (score >0) {
				addScore(-score, 1);
			}
			if (score2 >0) {
				addScore(-score2, 2);
				System.out.println(score2);
			}
			if (livesCount>0) {
				do {
					removeLife(1);
				}while(livesCount>0); 
			}
			if (livesCount2>0) {
				do {
					removeLife(2);
				}while(livesCount2>0); 
			}
		int perserve = BattleController.timesSnapped;
		BattleGame.getEnemies().clear();
		root.getChildren().removeAll(root.getChildren());
		BattleController.timesSnapped = perserve;
		BattleController.reset();
		
	}

	public static boolean isBeenRan() {
		return beenRan;
	}

	public static void setBeenRan(boolean beenRan) {
		BattleGame.beenRan = beenRan;
	}

	public static int getLivesCount(int player) {
		if (player==1) {
			return livesCount;
			} else {
				return livesCount2;
			}
	}


}
