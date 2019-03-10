package controllers;

import java.util.ArrayList;
import java.util.Random;

import application.Main;
import application.Sprite;
import enums.LaserType;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Laser;
import view.GameOverMenu;
import view.MainMenu;
import view.SinglePlayer;

public class SpaceInvaders {
	public   int countToBottom = 0;
	public   boolean gameRunning = true, gameOver = false;
	private  int frame = 0;
	private  int enemySpeed = 100;
	private  boolean playerMoving = false;
	private  boolean moveRight = true;
	public   Stage stage;
	private  ArrayList<Laser> lasers = new ArrayList<Laser>();
	private  int playerShots = 0;
	private  int frameLastShot = 120;
	private  Random rng = new Random();
	public   int timesSnapped = 0;
	public   int timeSinceBonusEnemy = 0;
	public   int howManyBonusEnemys = 0;
	private SinglePlayer currentLevel;
	
	public SinglePlayer getLevel() {
		return currentLevel;
	}
	
	public void start() {
		currentLevel = new SinglePlayer();
		timer.start();
	}
	
	private  AnimationTimer timer = new AnimationTimer() {
		@Override
		public void handle(long now) {
			if (gameRunning) {
				update();
			}
			controls(currentLevel.getScene());
		}
	};
	
	public void update() {
		currentLevel.setT(currentLevel.getT() + 1);
		if (currentLevel.getT() > 2) {
			for (Sprite s : currentLevel.getSprites()) {
				if (s.isOofed()) {
					s.updateHowLongBeenOofed();
				}

//					if (s.getType().equals("enemy")) {
//						int rand = rng.nextInt(100) % 10;
//
//						if (rand == 0) {
//
//							if (currentLevel.getEnemies().size() > 20) {
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
					currentLevel.getSprites().remove(s);

				} else {
					if (!s.getType().equals("player")) {
						s.setH(32);
						s.setW(16);
						s.setSpriteFile("explosion");
						currentLevel.addScore(10);
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

			currentLevel.setT(0);
		}

		if (currentLevel.getLivesCount() == 0) {
			gameRunning = false;
			gameOver = true;
			System.out.println("no life");
		}

		currentLevel.getPlayer().movePlayer();
		if (playerMoving) {
			enemySpeed -= 20;
		}

		if (frame % enemySpeed == 0) {
			if (moveRight) {
				for (Sprite e : currentLevel.getEnemies()) {
					e.moveRight();
				}
			} else {
				for (Sprite e : currentLevel.getEnemies()) {
					e.moveLeft();
				}
			}
		}
		if (playerMoving) {
			enemySpeed += 20;
		}

		boolean down = true;
		for (Sprite e : currentLevel.getEnemies()) {


			int X = (int) e.getTranslateX();

			if (!e.isOofed() && (X < 0 || X > 570)) {
				moveRight = !moveRight;
				System.out.println(e.getTranslateY());
				System.out.println(e.getTranslateX());
				System.out.println("count: " + countToBottom);
				if (countToBottom <= 275000 && countToBottom >= 17817) {
					gameOver = true;
					System.out.println("bottom");
					setEnemySpeed(Integer.MAX_VALUE - 1);
				}
				if (e.getTranslateX() < 0) {
					for (Sprite es : currentLevel.getEnemies()) {
						es.moveDown();
						es.moveRight();
						if (down) {
							down = !down;
						}
					}
				} else {
					for (Sprite es : currentLevel.getEnemies()) {

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
		
		if(currentLevel.getEnemies().size() == 0) {
			
			SinglePlayer newLevel = new SinglePlayer(currentLevel.getShields(),currentLevel.getScore(),currentLevel.getLives());
			currentLevel = newLevel;
			
			countToBottom = 0;
			gameRunning = true; 
			gameOver = false;
			frame = 0;
			enemySpeed = 100;
			playerMoving = false;
			moveRight = true;
			lasers = new ArrayList<Laser>();
			playerShots = 0;
			frameLastShot = 120;
			timesSnapped = 0;
			timeSinceBonusEnemy = 0;
			howManyBonusEnemys = 0;
			
			stage.setScene(newLevel.getScene());
			
		}

		checkIfLaserTouchesAnything();

		countToBottom++;
		
		int chance = rng.nextInt(20);
		
		if (chance == 0 && timeSinceBonusEnemy > 2000) {
			
			Sprite s = new Sprite(600, -600 - howManyBonusEnemys * 32, "bonusEnemy", "motherShip", 32, 11, 8);
			currentLevel.getMotherShips().add(s);
			currentLevel.getSwitchBox().getChildren().add(s);
			timeSinceBonusEnemy = 0;
			howManyBonusEnemys++;
			System.out.println("mother ship");
			
		}
		
		for (Sprite e : currentLevel.getMotherShips()) {
			
			e.moveLeft();
			
		}
		
		timeSinceBonusEnemy++;
		frame++;
		frameLastShot++;
	
		if (gameOver) {
			System.out.println("GAME OVER");
			gameOver = false;
			reset();
			MainMenu.getStage().setScene(GameOverMenu.getScene(MainMenu.getStage()));
		}
	}
	
	
	
	public   void populateEnemies() {
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
			currentLevel.getEnemies().add(s);
			currentLevel.getSwitchBox().getChildren().add(s);
			posX += 40;
			count++;
		}

	}

	private   void checkIfLaserTouchesAnything() {

		ArrayList<Laser> offed = new ArrayList<Laser>();
		ArrayList<Sprite> kaboomed = new ArrayList<Sprite>();

		for (Laser l : lasers) {
			if (l.getTimeAlive() > 300) {
				offed.add(l);
				System.out.println("Removed Laser");
			}
			for (Sprite s : currentLevel.getSprites()) {
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
						} else {

						}

					} else {
						kaboomed.add(s);
						frameLastShot = 120;
						offed.add(l);
					}

				}

			}
		}
		for (Laser l : offed) {
			l.getSprite().setTranslateY(42069);
			lasers.remove(l);
			currentLevel.getSprites().remove(l.getSprite());
		}
		boolean zoomUp = false;
		for (Sprite s : kaboomed) {
			if (s.getType().equals("player")) {
				currentLevel.removeLife();
				System.out.println("oof");
			}
			s.setOofed(true);
			if (s.getType().equals("enemy")) {
				currentLevel.getEnemies().remove(s);
				zoomUp = true;
			}
		}
		if (zoomUp) {
			for (Sprite e : currentLevel.getEnemies()) {
				e.setZoom(e.getZoom() + 1);
			}
		}

	}

	private void controls(Scene scene) {
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
			case A:
				if (gameRunning) {
					currentLevel.getPlayer().setSpeed(-5);
					playerMoving = true;
				}
				break;
			case D:
				if (gameRunning) {
					currentLevel.getPlayer().setSpeed(5);
					playerMoving = true;
				}
				break;
			case SPACE:
				if (gameRunning) {
					if (frameLastShot > 110) {
						int[] pos = { (int) currentLevel.getPlayer().getSprite().getTranslateX(), 0 };
						Laser laser = new Laser(1, LaserType.NORMAL,
								new Sprite(pos[0] + 14, 0, "laser", "PlayerLaser", 4, 32, 8));
						laser.getSprite().setTranslateY(-980 - (playerShots * 32) - (timesSnapped * 9000));
						lasers.add(laser);
						currentLevel.getSwitchBox().getChildren().add(laser.getSprite());
						playerShots++;
						frameLastShot = 0;
					}
					System.out.println(frameLastShot + "----------------");
				}
				break;
			case ESCAPE:
				if(!gameOver) {
					gameRunning = !gameRunning;
				}
				break;
			case L:
				if (gameRunning) {
					if (currentLevel.getLives().size() > 0) {
						currentLevel.removeLife();
					}
				}
				break;
			case BACK_SPACE:
				if (gameRunning) {
					int[] pos = { (int) currentLevel.getPlayer().getSprite().getTranslateX(), 0 };
					Laser laser = new Laser(1, LaserType.NORMAL,
							new Sprite(pos[0] + 14, 0, "laser", "PlayerLaser", 4, 32, 8));
					laser.getSprite().setTranslateY(-980 - (playerShots * 32) - (timesSnapped * 9000));
					lasers.add(laser);
					currentLevel.getSwitchBox().getChildren().add(laser.getSprite());
					playerShots++;
					frameLastShot = 0;
				}
			default:
				break;
			}
		});
		scene.setOnKeyReleased((e -> {
			switch (e.getCode()) {
			case A:
				if (gameRunning) {
					currentLevel.getPlayer().setSpeed(0);
					playerMoving = false;
				}
				break;
			case D:
				if (gameRunning) {
					currentLevel.getPlayer().setSpeed(0);
					playerMoving = false;
				}
				break;
			default:
				break;
			}
		}));
	}

	public void startApp(String[] args) {

		Main.main(args);

	}

	public int getEnemySpeed() {
		return enemySpeed;
	}

	public void setEnemySpeed(int enemySpeed) {
		this.enemySpeed = enemySpeed;
	}

	public void reset() {
		frame = 0;
		enemySpeed = 10;
		playerMoving = false;
		moveRight = true;
		playerShots = 0;
		frameLastShot = 120;
		gameOver = false;

	}
}

