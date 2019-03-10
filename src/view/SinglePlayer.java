package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import application.Sprite;
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
	
	private  boolean beenRan = false;
	private  VBox root = new VBox();
	private  Scene scene = new Scene(root, 600, 600);
	private  Stage mainStage;
	private  boolean isInited = false;
	private  Player player = new Player(1);
	private  Sprite playerSprite = player.getSprite();
	private  ArrayList<Sprite> enemies = new ArrayList<>();
	private  ArrayList<Sprite> shields = new ArrayList<>();
	private  ArrayList<Sprite> lives = new ArrayList<>();
	private  VBox switchBox;
	private  VBox scoreBox;
	private  int t;
	private  int score = 0;
	private  int livesCount;
	private  ArrayList<Sprite> motherShips = new ArrayList<Sprite>();
	
	private  Text scoreText = new Text(0, 0, "Score: " + Integer.toString(score));
	
	public SinglePlayer() {
		initStage();
	}

	public SinglePlayer(ArrayList<Sprite> shields2, int score2, ArrayList<Sprite> lives2) {
		
		initNextLevel(shields2, score2, lives2);
		
	}

	public  ArrayList<Sprite> getMotherShips(){
		return motherShips;
	}
	
	public  VBox getRoot() {
		return root;
	}

	public  void setRoot(VBox root) {
		this.root = root;
	}

	public  Scene getScene() {
		return scene;
	}

	public  void setScene(Scene scene) {
		this.scene = scene;
	}

	public  Stage getMainStage() {
		return mainStage;
	}

	public  void setMainStage(Stage mainStage) {
		this.mainStage = mainStage;
	}

	public  boolean isInited() {
		return isInited;
	}

	public  void setInited(boolean isInited) {
		this.isInited = isInited;
	}

	public  Player getPlayer() {
		return player;
	}

	public  ArrayList<Sprite> getEnemies() {
		return enemies;
	}

	public  void setEnemies(ArrayList<Sprite> enemies) {
		this.enemies = enemies;
	}

	public  ArrayList<Sprite> getShields() {
		return shields;
	}

	public  void setShields(ArrayList<Sprite> shields) {
		this.shields = shields;
	}

	public  VBox getSwitchBox() {
		return switchBox;
	}

	public  void setSwitchBox(VBox switchBox) {
		this.switchBox = switchBox;
	}

	public  int getT() {
		return t;
	}

	public  void setT(int t) {
		this.t = t;
	}

	
	public  void addScore(int addScore) {
		score += addScore;
		getScoreText().setText("Score: "+Integer.toString(score));
	}
	public  int getScore() {
		return score;
	}


	public  void initStage() {
//		whoIs.setResizable(false);
		root.setAlignment(Pos.CENTER);
		switchBox = new VBox();
		switchBox.setMinHeight(600);

		switchBox.getChildren().add(getPlayerSprite());
		populateShields();
		populateEnemies();

		BackgroundFill back = new BackgroundFill(Color.BLACK, new CornerRadii(1), null);
		switchBox.setBackground(new Background(back));
		root.getChildren().addAll(switchBox);

		setScoreBox(new VBox());
		getScoreBox().setMinHeight(50);
		getScoreBox().getChildren().add(getScoreText());
		getScoreText().setFill(Color.WHITE);
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			getScoreText().setFont(Font.loadFont(is, 23));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		getScoreBox().setBackground(new Background(back));
		populateLives();

		root.getChildren().add(getScoreBox());

		isInited = true;

	}
	
	public  void initNextLevel(ArrayList<Sprite> shields2, int score2, ArrayList<Sprite> lives2) {
//		whoIs.setResizable(false);
		root.setAlignment(Pos.CENTER);
		switchBox = new VBox();
		switchBox.setMinHeight(600);
		score = score2;
		scoreText = new Text(0, 0, "Score: " + Integer.toString(score));

		switchBox.getChildren().add(getPlayerSprite());
		shields = shields2;
		switchBox.getChildren().addAll(shields);
		populateEnemies();

		BackgroundFill back = new BackgroundFill(Color.BLACK, new CornerRadii(1), null);
		switchBox.setBackground(new Background(back));
		root.getChildren().addAll(switchBox);

		setScoreBox(new VBox());
		getScoreBox().setMinHeight(50);
		getScoreBox().getChildren().add(getScoreText());
		getScoreText().setFill(Color.WHITE);
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			getScoreText().setFont(Font.loadFont(is, 23));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		getScoreBox().setBackground(new Background(back));
		lives = lives2;
		setLivesCount(lives.size());
		getScoreBox().getChildren().addAll(lives);

		root.getChildren().add(getScoreBox());

	}

	private  void populateShields() {
		int posX = 50;
		int posY = 400;

		for (int i = 0; i < 4; i++) {
			Sprite s = new Sprite(posX, posY - i * 41, "shield", "Shield", 64, 41, 10);
			shields.add(s);
			switchBox.getChildren().add(s);
			posX += 140;
		}

	}
	
	private  void populateLives() {
		int posX = 400;
		int posY = -23;

		for (int i = 0; i < player.getLives(); i++) {
			Sprite s = new Sprite(posX, posY - i * 24, "live", "idle", 32, 24, 1);
			lives.add(s);
			getScoreBox().getChildren().add(lives.get(i));
			posX += 40;
			setLivesCount(getLivesCount() + 1);
		}

	}

	public  void populateEnemies() {
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

	private  List<Sprite> sprites() {
		return switchBox.getChildren().stream().map(n -> (Sprite) n).collect(Collectors.toList());
	}

	public  List<Sprite> getSprites() {
		return sprites();
	}

	public  ArrayList<Sprite> getLives() {
		return lives;
	}

	public  void setLives(ArrayList<Sprite> lives) {
		this.lives = lives;
	}
	public  void removeLife() {
		getScoreBox().getChildren().remove(lives.remove(lives.size()-1));
		setLivesCount(getLivesCount() - 1);
	}

	public  boolean isBeenRan() {
		return beenRan;
	}

	public  void setBeenRan(boolean beenRan) {
		this.beenRan = beenRan;
	}

	public  int getLivesCount() {
		return livesCount;
	}

	public  Sprite getPlayerSprite() {
		return playerSprite;
	}

	public  void setPlayerSprite(Sprite playerSprite) {
		this.playerSprite = playerSprite;
	}

	public  VBox getScoreBox() {
		return scoreBox;
	}

	public  void setScoreBox(VBox scoreBox) {
		this.scoreBox = scoreBox;
	}

	public  Text getScoreText() {
		return scoreText;
	}

	public  void setScoreText(Text scoreText) {
		this.scoreText = scoreText;
	}

	public  void setLivesCount(int livesCount) {
		this.livesCount = livesCount;
	}


}
