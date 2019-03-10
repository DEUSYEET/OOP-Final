package controllers;

import java.awt.Rectangle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Random;

import application.Main;
import application.Sprite;
import enums.LaserType;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import models.Laser;
import view.GameOverMenu;
import view.MainMenu;
import view.SinglePlayer;

public class SpaceInvaders {
	public static int countToBottom;
	public static boolean gameRunning = false, gameOver = false;

	private static int frame = 0;
	private static int enemySpeed = 100;
	private static boolean playerMoving = false;
	private static boolean moveRight = true;
	private static ArrayList<Sprite> enemies = new ArrayList<>();
	private static ArrayList<Sprite> shields = new ArrayList<>();
	private static ArrayList<Sprite> lives = new ArrayList<>();
	private static VBox root = new VBox();
	private static Scene scene = new Scene(root, 600, 600);
	private static ArrayList<Laser> lasers = new ArrayList<Laser>();
	private static int playerShots = 0;
	private static int frameLastShot = 120;
	private static int howFarOffScreen = 0;
	private static int shootRow = -1280;
	private static Random rng = new Random();
	public static int timesSnapped = 0;
	private static int zoomie = 5;
	public static int timeSinceBonusEnemy = 0;
	public static int howManyBonusEnemys = 0;
	
	public static void update() {
		if (gameRunning) {
			SinglePlayer.setT(SinglePlayer.getT() + 1);
			if (SinglePlayer.getT() > 2) {
				for (Sprite s : SinglePlayer.getSprites()) {
					if (s.isOofed()) {
						s.updateHowLongBeenOofed();
					}

//					if (s.getType().equals("enemy")) {
//						int rand = rng.nextInt(100) % 10;
//
//						if (rand == 0) {
//
//							if (SinglePlayer.getEnemies().size() > 20) {
//								if (rng.nextInt() % 100 == 0) {
//
//									shoot(s);
//								}
//							} else {
//								if (rng.nextInt() % 1000 == 0) {
//
//									shoot(s);
//								}
//							}
//
//						}
//					}

					if (s.getHLBO() < 1 || (!s.isOofed())) {

						if (s.getType().equals("shield")) {
						} else {
							s.update();
						}
					}

					else if (s.getHLBO() > 10 && s.isOofed()) {
						s.setTranslateY(42069);
						SinglePlayer.getSprites().remove(s);

					} else {
						if (!s.getType().equals("player")) {
							s.setH(32);
							s.setW(16);
							s.setSpriteFile("explosion");
							SinglePlayer.addScore(10);
						} else if (s.getType().equals("player")) {
							s.setSpriteFile("playerHit");
							System.out.println(s.getHLBO());
						}
					}

					if (s.getType().equals("player")) {
						s.setSpriteFile("idle");
						s.setH(24);
						s.setW(32);
						s.setOofed(false);
						s.setHLBO(-1);
						continue;
					}

				}

				SinglePlayer.setT(0);
			}

			if (SinglePlayer.getLivesCount() == 0) {
				gameRunning = false;
				gameOver = true;
			}

			controls(SinglePlayer.getScene());
			SinglePlayer.getPlayer().movePlayer();
			if (playerMoving) {
				enemySpeed -= 20;
			}

			if (frame % enemySpeed == 0) {
				if (moveRight) {
					for (Sprite e : SinglePlayer.getEnemies()) {
						e.moveRight();
					}
				} else {
					for (Sprite e : SinglePlayer.getEnemies()) {
						e.moveLeft();
					}
				}
			}
			if (playerMoving) {
				enemySpeed += 20;
			}

			boolean down = true;
			for (Sprite e : SinglePlayer.getEnemies()) {


				int X = (int) e.getTranslateX();

				if (!e.isOofed() && (X < 0 || X > 570)) {
					moveRight = !moveRight;
					System.out.println(e.getTranslateY());
					System.out.println(e.getTranslateX());
					System.out.println("count: " + countToBottom);
					if (countToBottom <= 275000 && countToBottom >= 17817) {
						gameOver = true;
						setEnemySpeed(Integer.MAX_VALUE - 1);
					}
					if (e.getTranslateX() < 0) {
						for (Sprite es : SinglePlayer.getEnemies()) {
							es.moveDown();
							es.moveRight();
							if (down) {
								shootRow += 10;
								down = !down;
							}
						}
					} else {
						for (Sprite es : SinglePlayer.getEnemies()) {

							es.moveDown();
							es.moveLeft();

						}
					}
					break;
				}
				down = true;
			}

			for (Laser l : lasers) {
				l.setTimeAlive(+1);

				if (frame % l.getSpeed() == 0 && l.getType().equals(LaserType.NORMAL)) {
					l.getSprite().moveUp();
				} else if (frame % l.getSpeed() == 0 && l.getType().equals(LaserType.ALIEN)) {
					l.getSprite().moveDown();
				}
				
			}
			
			if(SinglePlayer.getEnemies().size() == 0) {
				SinglePlayer.getSprites().clear();
				for(Sprite e : SinglePlayer.getEnemies()) {
					e.setZoom(zoomie + 1);
					System.out.println(e.getId() + " " + e.getZoom());
				}
				zoomie++;
				initNewLVL();
//				for (Sprite e : SinglePlayer.getEnemies()) {
//					e.setTranslateX(5);
//				}
//				SpaceInvaders.countToBottom = 0;
//				SinglePlayer.addScore(-SinglePlayer.getScore());
//				SpaceInvaders.gameRunning = true;
//				//SinglePlayer.snap();
//				SinglePlayer.setInited(false);
//				mainStage.setScene(SinglePlayer.getScene(whoIs));
				System.out.println("Populate Enemys");
			}

			checkIfLaserTouchesAnything();

			countToBottom++;
			if (SinglePlayer.getEnemies().isEmpty()) {
				SinglePlayer.populateEnemies();
			}
			
			int chance = rng.nextInt(20);
			
			if (chance == 0 && timeSinceBonusEnemy > 2000) {
				
				Sprite s = new Sprite(600, -600 - howManyBonusEnemys * 32, "bonusEnemy", "motherShip", 32, 11, 8);
				SinglePlayer.getMotherShips().add(s);
				SinglePlayer.switchBox.getChildren().add(s);
				timeSinceBonusEnemy = 0;
				howManyBonusEnemys++;
				System.out.println("mother ship");
				
			}
			
			for (Sprite e : SinglePlayer.getMotherShips()) {
				
				e.moveLeft();
				
			}
			
			timeSinceBonusEnemy++;
			frame++;
			frameLastShot++;
		}
		if (gameOver) {
			System.out.println("GAME OVER");
			gameOver = false;
			SinglePlayer.snap();
			reset();
			MainMenu.getStage().setScene(GameOverMenu.getScene(MainMenu.getStage()));
		}
	}

	public static void initNewLVL() {
		root.setAlignment(Pos.CENTER);
		VBox testRoot = new VBox();
		Scene scene = new Scene(testRoot, 600, 600);
		SinglePlayer.setSwitchBox(new VBox());
		SinglePlayer.getSwitchBox().setMinHeight(600);

		SinglePlayer.getSwitchBox().getChildren().add(SinglePlayer.getPlayerSprite());
		populateShields();
		populateEnemies();
		Random rng = new Random();
		BackgroundFill back = null;
		switch(rng.nextInt(10)) {
		case 0:
			back = new BackgroundFill(Color.BLACK, new CornerRadii(1), null);			
			break;
		case 1:
			back = new BackgroundFill(Color.BLUE, new CornerRadii(1), null);			
			break;
		case 2:
			back = new BackgroundFill(Color.BROWN, new CornerRadii(1), null);			
			break;
		case 3:
			back = new BackgroundFill(Color.CHOCOLATE, new CornerRadii(1), null);			
			break;
		case 4:
			back = new BackgroundFill(Color.CORNFLOWERBLUE, new CornerRadii(1), null);			
			break;
		case 5:
			back = new BackgroundFill(Color.CRIMSON, new CornerRadii(1), null);			
			break;
		case 6:
			back = new BackgroundFill(Color.DARKBLUE, new CornerRadii(1), null);			
			break;
		case 7:
			back = new BackgroundFill(Color.DARKGREEN, new CornerRadii(1), null);			
			break;
		case 8:
			back = new BackgroundFill(Color.DARKMAGENTA, new CornerRadii(1), null);			
			break;
		case 9:
			back = new BackgroundFill(Color.DARKRED, new CornerRadii(1), null);			
			break;
		}
		SinglePlayer.getSwitchBox().setBackground(new Background(back));
		root.getChildren().addAll(SinglePlayer.getSwitchBox());

		SinglePlayer.setScoreBox(new VBox());
		SinglePlayer.getScoreBox().setMinHeight(50);
		SinglePlayer.getScoreBox().getChildren().add(SinglePlayer.getScoreText());
		SinglePlayer.getScoreText().setFill(Color.WHITE);
		try {
			InputStream is = new FileInputStream("src/assets/pixel.ttf");
			SinglePlayer.getScoreText().setFont(Font.loadFont(is, 23));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		SinglePlayer.getScoreBox().setBackground(new Background(back));
		populateLives();

		root.getChildren().add(SinglePlayer.getScoreBox());

		SinglePlayer.getTimer().start();
	}
	
	private static void populateLives() {
		int posX = 400;
		int posY = -23;

		for (int i = 0; i < SinglePlayer.getPlayer().getLives(); i++) {
			Sprite s = new Sprite(posX, posY - i * 24, "live", "idle", 32, 24, 1);
			SinglePlayer.getLives().add(s);
			SinglePlayer.getScoreBox().getChildren().add(SinglePlayer.getLives().get(i));
			posX += 40;
			SinglePlayer.setLivesCount(SinglePlayer.getLivesCount() + 1);
		}

	}
	
	private static void populateShields() {
		for (Sprite s : SinglePlayer.getShields()) {
			shields.addAll(SinglePlayer.getShields());
			SinglePlayer.getSwitchBox().getChildren().add(s);
		}

	}
	
	
	
	public static void populateEnemies() {
		int posX = 405;
		int posY = -560;
		int count = 0;
		String sprites[] = { "enemy1", "enemy2", "enemy3", "enemy4" };
		int X[] = { 20, 24, 18, 28 };
//		int Y[] = {22,25,25,22};
		int file = 3;

		for (int i = 0; i < 40; i++) {
			if (count >= 10) {
				posY += 50;
				posX = 405;
				count = 0;
				file--;
			}
			Sprite s = new Sprite(posX, posY - i * 32, "enemy", sprites[file], X[file], 32, 8);
			SinglePlayer.getEnemies().add(s);
			SinglePlayer.getSwitchBox().getChildren().add(s);
			posX += 40;
			count++;
		}

	}
	
	private static int enemyShots;

	private static void shoot(Sprite s) {
		int[] pos = { (int) s.getTranslateX(), (int) s.getTranslateY() };

		Laser laser = new Laser(1, LaserType.ALIEN, new Sprite(pos[0] + 14, 0, "laser", "EnemyLaser", 4, 32, 8));
		laser.getSprite().setTranslateY(shootRow - (playerShots * 32));
		lasers.add(laser);
		SinglePlayer.getSwitchBox().getChildren().add(laser.getSprite());
		playerShots++;
	}

	private static void checkIfLaserTouchesAnything() {

		ArrayList<Laser> offed = new ArrayList<Laser>();
		ArrayList<Sprite> kaboomed = new ArrayList<Sprite>();

		for (Laser l : lasers) {
			if (l.getTimeAlive() > 300) {
				offed.add(l);
				System.out.println("Removed Laser");
			}
			for (Sprite s : SinglePlayer.getSprites()) {
				if (s.getType().equals("enemy") && l.getType() == LaserType.ALIEN
						&& l.getSprite().getBoundsInParent().intersects(s.getBoundsInParent())
						&& !l.getSprite().equals(s)) {
				} else if (l.getSprite().getBoundsInParent().intersects(s.getBoundsInParent())
						&& !l.getSprite().equals(s) && !s.isOofed()) {
					// kaboom

					if (s.getType().equals("shield")) {
						if (s.getCurrentFrame() <= 8) {
							s.update();
							frameLastShot = 120;
							offed.add(l);
							howFarOffScreen++;
						} else {

						}

					} else {
						kaboomed.add(s);
						frameLastShot = 120;
						offed.add(l);
						howFarOffScreen++;
					}

				}

			}
		}
		for (Laser l : offed) {
			l.getSprite().setTranslateY(42069);
			lasers.remove(l);
			SinglePlayer.getSprites().remove(l.getSprite());
		}
		boolean zoomUp = false;
		for (Sprite s : kaboomed) {
			if (s.getType().equals("player")) {
				SinglePlayer.removeLife();
			}
			s.setOofed(true);
			if (s.getType().equals("enemy")) {
				SinglePlayer.getEnemies().remove(s);
				zoomUp = true;
			}
		}
		if (zoomUp) {
			for (Sprite e : SinglePlayer.getEnemies()) {
				e.setZoom(e.getZoom() + 1);
			}
		}

	}

	private static void controls(Scene scene) {
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case A:
				SinglePlayer.getPlayer().setSpeed(-5);
				playerMoving = true;
				break;
			case D:
				SinglePlayer.getPlayer().setSpeed(5);
				playerMoving = true;
				break;
			case SPACE:
				if (frameLastShot > 110) {
					int[] pos = { (int) SinglePlayer.getPlayer().getSprite().getTranslateX(), 0 };
					Laser laser = new Laser(1, LaserType.NORMAL,
							new Sprite(pos[0] + 14, 0, "laser", "PlayerLaser", 4, 32, 8));
					laser.getSprite().setTranslateY(-980 - (playerShots * 32) - (timesSnapped * 9000));
					lasers.add(laser);
					SinglePlayer.getSwitchBox().getChildren().add(laser.getSprite());
					playerShots++;
					frameLastShot = 0;
				}
				System.out.println(frameLastShot + "----------------");
				break;
			case ESCAPE:
				if(gameRunning = true) {
					SinglePlayer.getTimer().stop();
				}
				break;
			case L:
				if (SinglePlayer.getLives().size() > 0) {
					SinglePlayer.removeLife();
				}
				break;
			case BACK_SPACE:
				int[] pos = { (int) SinglePlayer.getPlayer().getSprite().getTranslateX(), 0 };
				Laser laser = new Laser(1, LaserType.NORMAL,
						new Sprite(pos[0] + 14, 0, "laser", "PlayerLaser", 4, 32, 8));
				laser.getSprite().setTranslateY(-980 - (playerShots * 32) - (timesSnapped * 9000));
				lasers.add(laser);
				SinglePlayer.getSwitchBox().getChildren().add(laser.getSprite());
				playerShots++;
				frameLastShot = 0;
			default:
				break;
			}
		});
		scene.setOnKeyReleased((e -> {
			switch (e.getCode()) {
			case A:
				SinglePlayer.getPlayer().setSpeed(0);
				playerMoving = false;
				break;
			case D:
				SinglePlayer.getPlayer().setSpeed(0);
				playerMoving = false;
				break;
			case SPACE:
				break;
			default:
				break;
			}
		}));
	}

	public static void startApp(String[] args) {

		Main.main(args);

	}

	public static int getEnemySpeed() {
		return enemySpeed;
	}

	public static void setEnemySpeed(int enemySpeed) {
		SpaceInvaders.enemySpeed = enemySpeed;
	}

	public static void reset() {
		frame = 0;
		enemySpeed = 10;
		playerMoving = false;
		moveRight = true;
		shootRow = -1280;
		playerShots = 0;
		frameLastShot = 120;
		howFarOffScreen = 0;
		gameOver = false;

	}
}

