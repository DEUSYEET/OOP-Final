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

public class CoOPDLC {
	

	
	private  boolean beenRan = false;
	private  VBox root = new VBox();
	private  Scene scene = new Scene(root, 1200, 600);
	private  Stage mainStage;
	private  boolean isInited = false;
	private  Player player1 = new Player(1);
	private  Player player2 = new Player(3);
	private  Sprite player1Sprite = player1.getSprite();
	private  Sprite player2Sprite = player2.getSprite();
	private  ArrayList<Sprite> enemies = new ArrayList<>();
	private  ArrayList<Sprite> shields = new ArrayList<>();
	private  ArrayList<Sprite> lives1 = new ArrayList<>();
	private  VBox switchBox;
	private  VBox scoreBox;
	private  int t;
	private  int score1 = 0;
	private  int livesCount;
	private  ArrayList<Sprite> motherShips = new ArrayList<Sprite>();
	
	private  Text scoreText = new Text(0, 0, "Score: " + Integer.toString(score1));
	
	public CoOPDLC() {
		initStage();
	}

	public CoOPDLC(ArrayList<Sprite> shields2, int score2, ArrayList<Sprite> lives2) {
		
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

	public  Player getPlayer1() {
		return player1;
	}
	
	public  Player getPlayer2() {
		return player2;
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
		score1 += addScore;
		getScoreText().setText("Score: "+Integer.toString(score1));
	}
	public  int getScore() {
		return score1;
	}


	public  void initStage() {
//		whoIs.setResizable(false);
		root.setAlignment(Pos.CENTER);
		switchBox = new VBox();
		switchBox.setMinHeight(600);

		switchBox.getChildren().add(getPlayer1Sprite());
		player2.getSprite().setTranslateY(player2.getSprite().getTranslateY() - 24);
		player2.getSprite().setTranslateX(player2.getSprite().getTranslateX() - 32);
		switchBox.getChildren().add(getPlayer2Sprite());
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
		score1 = score2;
		scoreText = new Text(0, 0, "Score: " + Integer.toString(score1));

		switchBox.getChildren().add(getPlayer1Sprite());
		switchBox.getChildren().add(getPlayer2Sprite());
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
		lives1 = lives2;
		setLivesCount(lives1.size());
		getScoreBox().getChildren().addAll(lives1);

		root.getChildren().add(getScoreBox());

	}

	private  void populateShields() {
		int posX = 50;
		int posY = 400;

		for (int i = 0; i < 8; i++) {
			Sprite s = new Sprite(posX, posY - i * 41, "shield", "Shield", 64, 41, 10);
			shields.add(s);
			switchBox.getChildren().add(s);
			posX += 140;
		}

	}
	
	private  void populateLives() {
		int posX = 400;
		int posY = -23;
		
		player1.setLives(6);
		
		for (int i = 0; i < player1.getLives(); i++) {
			Sprite s = new Sprite(posX, posY - i * 24, "live", "idle1", 32, 24, 1);
			lives1.add(s);
			getScoreBox().getChildren().add(lives1.get(i));
			posX += 40;
			setLivesCount(getLivesCount() + 1);
		}

	}

	public  void populateEnemies() {
		int posX = 5;
		int posY = -352;
		int count = 0;
		String sprites[] = { "enemy1", "enemy2", "enemy3", "enemy4" };
		int X[] = { 20, 24, 18, 28 };
//		int Y[] = {22,25,25,22};
		int file = 3;

		for (int i = 0; i < 80; i++) {
			if (count >= 20) {
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
		return lives1;
	}

	public  void setLives(ArrayList<Sprite> lives) {
		this.lives1 = lives;
	}
	public  void removeLife() {
		getScoreBox().getChildren().remove(lives1.remove(lives1.size()-1));
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

	public  Sprite getPlayer1Sprite() {
		return player1Sprite;
	}
	
	public  Sprite getPlayer2Sprite() {
		return player2Sprite;
	}

	public  void setPlayerSprite(Sprite playerSprite) {
		this.player1Sprite = playerSprite;
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
