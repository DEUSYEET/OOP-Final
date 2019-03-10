package controllers;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import application.Main;
import application.Sprite;
import enums.LaserType;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import models.Laser;
import view.GameOverMenu;
import view.MainMenu;
import view.CompetetiveGame;

public class CompetetiveController {
	public static int countToBottom;
	public static boolean gameRunning = false, gameOver = false;
	private static int frame = 0;
	private static int enemySpeed = 10;
	private static boolean playerMoving = false;
	private static boolean moveRight = true;
	private static ArrayList<Laser> lasers = new ArrayList<Laser>();
	private static int playerShots = 0;
	private static int frameLastShot = 120;
	private static int enemyShots;
	private static int frameLastShot2;
	private static int howFarOffScreen = 0;
	private static int shootRow=-1800;
	private static Random rng = new Random();
	public static int timesSnapped = 0;
	

	public static void update() {
		if (gameRunning) {
			CompetetiveGame.setT(CompetetiveGame.getT() + 1);
			if (CompetetiveGame.getT() > 2) {
				for (Sprite s : CompetetiveGame.getSprites()) {
					if (s.isOofed()) {
						s.updateHowLongBeenOofed();
					}
					if (s.getType().equals("enemy")) {
						int rand = rng.nextInt(100)% 10;

						if (rand == 0) {
						
							if (CompetetiveGame.getEnemies().size() > 20) {
								if (rng.nextInt() % 100 == 0) {

									shoot(s);
								}
							} else {
								if (rng.nextInt() % 10 == 0) {

									shoot(s);
								}
							}

						}
					}
					if (s.getHLBO() < 1 || (!s.isOofed())) {
						
						if (s.getType().equals("shield")) {
						} else {
							s.update();
						}
					}

					else if (s.getHLBO() > 10 && s.isOofed()) {

						if (s.getType().equals("player1")) {
							s.setSpriteFile("idle1");
							s.setOofed(false);
							s.setHLBO(-1);
							s.update();
							System.out.println(s.getSpriteFile());
							continue;
						} else if (s.getType().equals("player2")) {
							s.setH(24);
							s.setW(32);
							s.setSpriteFile("idle3");
							s.setOofed(false);
							s.setHLBO(-1);
							s.update();
							System.out.println(s.getSpriteFile());
							continue;
						}

						s.setTranslateY(42069);
						CompetetiveGame.getSprites().remove(s);

					} else {
						 if (s.getType().equals("player1")) {
								s.setSpriteFile("playerHit");
								System.out.println(s.getHLBO());
							} else if (s.getType().equals("player2")) {
								s.setSpriteFile("playerHit2");
								System.out.println(s.getHLBO());
							}
						 else if (!s.getType().equals("player1")||!s.getType().equals("player2")) {
								//System.out.println(s.getType());
								s.setH(32);
								s.setW(16);
								s.setSpriteFile("explosion");
						
							}
					}

				}

				CompetetiveGame.setT(0);
			}

			if (CompetetiveGame.getLivesCount(1) == 0||CompetetiveGame.getLivesCount(0) == 0) {
				if (CompetetiveGame.getLivesCount(1) == 0) {
					getYOTE(CompetetiveGame.getPlayer(1).getSprite());
				} if (CompetetiveGame.getLivesCount(2) == 0) {
					getYOTE(CompetetiveGame.getPlayer(2).getSprite());
				}
				if (CompetetiveGame.getLivesCount(1) == 0 &&CompetetiveGame.getScore(2)>CompetetiveGame.getScore(1)) {
				gameRunning = false;
				gameOver = true;
				} else if (CompetetiveGame.getLivesCount(2) == 0 &&CompetetiveGame.getScore(1)>CompetetiveGame.getScore(2)) {
					gameRunning = false;
					gameOver = true;
				}
			}


			controls(CompetetiveGame.getScene());
			CompetetiveGame.getPlayer(1).movePlayer();
			CompetetiveGame.getPlayer(2).movePlayer();
			if (playerMoving) {
				//enemySpeed -= 20;
			}

			if (frame % enemySpeed == 0) {
				int[] pos = { (int) CompetetiveGame.getPlayer(1).getSprite().getTranslateX(), 0 };
				if (moveRight) {
					for (Sprite e : CompetetiveGame.getEnemies()) {
						e.moveRight();
					}
				} else {
					for (Sprite e : CompetetiveGame.getEnemies()) {
						e.moveLeft();
					}
				}
			}
			if (playerMoving) {
				//enemySpeed += 20;
			}
			boolean down = true;
			for (Sprite e : CompetetiveGame.getEnemies()) {

				if (e.getType().equals("enemy1")){
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
						for (Sprite es : CompetetiveGame.getEnemies()) {
							es.moveDown();
							es.moveRight();
							if (down) {
								shootRow += 10;
								down = !down;
							}
						}
					} else {
						for (Sprite es : CompetetiveGame.getEnemies()) {

							es.moveDown();
							es.moveLeft();

						}
					}
					break;
				}
				down = true;
			}
			}
			for (Laser l : lasers) {
				l.setTimeAlive(+1);
				
				if (frame % l.getSpeed() == 0 && l.getType().equals(LaserType.NORMAL)) {
					if (l.getSprite().getType().equals("laser")) {
					l.getSprite().moveUp();
					} else {
						l.getSprite().moveDown();
					}
				} else if (frame % l.getSpeed() == 0 && l.getType().equals(LaserType.ALIEN)) {
				 if (l.getSprite().getType().equals("elaser1")) {
					l.getSprite().moveDown();
				 } else {
					 l.getSprite().moveUp();
				 }
				}
			}

			checkIfLaserTouchesAnything();

			if (CompetetiveGame.getEnemies().isEmpty()) {
				CompetetiveGame.populateEnemies();
			}

			frame++;
			frameLastShot++;
			frameLastShot2++;
		}
		if (gameOver) {
			System.out.println("GAME OVER");
			gameOver = false;
			CompetetiveGame.snap();
			reset();
			MainMenu.getStage().setScene(GameOverMenu.getScene(MainMenu.getStage()));
		}
	}

	private static void getYOTE(Sprite s) {
		s.setTranslateX(133742069);
		s.setTranslateY(133742069);
	}
	
	
	
	private static boolean flip = true;
	private static void shoot(Sprite s) {
		int rand = rng.nextInt(100) + 1;
		int[] pos = { (int) s.getTranslateX(), (int) s.getTranslateY() };
		System.out.println(pos[1]);
		//System.out.println(flip);
		if (flip) {
			Laser laser = new Laser(1, LaserType.ALIEN, new Sprite(pos[0] + 14, 0, "elaser1", "EnemyLaser", 4, 32, 8));
			laser.getSprite().setTranslateY(shootRow - (playerShots * 32));
			lasers.add(laser);
			CompetetiveGame.getSwitchBox().getChildren().add(laser.getSprite());
			playerShots++;
			flip = !flip;
		} else {
			Laser laser = new Laser(1, LaserType.ALIEN, new Sprite(pos[0] + 14, 0, "elaser2", "EnemyLaser", 4, 32, 8));
			laser.getSprite().setTranslateY(shootRow - (playerShots * 32));
			lasers.add(laser);
			CompetetiveGame.getSwitchBox().getChildren().add(laser.getSprite());
			playerShots++;
			flip = !flip;
		}
	}

	private static void checkIfLaserTouchesAnything() {

		ArrayList<Laser> offed = new ArrayList<Laser>();
		ArrayList<Sprite> kaboomed = new ArrayList<Sprite>();

		for (Laser l : lasers) {
			if (l.getTimeAlive()>300) {
				offed.add(l);
				System.out.println("Removed Laser");
			}
			for (Sprite s : CompetetiveGame.getSprites()) {
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
							frameLastShot2 = 120;
							offed.add(l);
							howFarOffScreen++;
						} else {

						}

					} else {
						kaboomed.add(s);
						frameLastShot = 120;
						frameLastShot2 = 120;
						if (l.getSprite().getType().equals("laser")) {
							CompetetiveGame.addScore(100,1);
						} else if (l.getSprite().getType().equals("laser2")) {
							CompetetiveGame.addScore(100,2);
						}
						offed.add(l);
						howFarOffScreen++;
					}

				}


			}
		}
		for (Laser l : offed) {
			l.getSprite().setTranslateY(42069);
			lasers.remove(l);
			CompetetiveGame.getSprites().remove(l.getSprite());
		}
		boolean zoomUp = false;
		for (Sprite s : kaboomed) {
			if (s.getType().equals("player1")) {
				CompetetiveGame.removeLife(1);
			} else if (s.getType().equals("player2")) {
				CompetetiveGame.removeLife(2);
			}
			s.setOofed(true);
			if (s.getType().equals("enemy")) {
				CompetetiveGame.getEnemies().remove(s);
				zoomUp = true;
			}
		}
		if (zoomUp) {
			for (Sprite e : CompetetiveGame.getEnemies()) {
				e.setZoom(e.getZoom() + 1);
			}
		}

	}



	private static void controls(Scene scene) {
		scene.setOnKeyPressed(e -> {
			System.out.println(e.getCode());
			switch (e.getCode()) {
			
			
			
			
			case A:
				CompetetiveGame.getPlayer(1).setSpeed(-5);
				playerMoving = true;
				break;
			case D:
				CompetetiveGame.getPlayer(1).setSpeed(5);
				playerMoving = true;
				break;
			case W:
				if (frameLastShot > 110) {
					int[] pos = { (int) CompetetiveGame.getPlayer(1).getSprite().getTranslateX(), 0 };
					Laser laser = new Laser(1, LaserType.NORMAL,
							new Sprite(pos[0] + 14, 0, "laser", "PlayerLaser", 4, 32, 8));
					laser.getSprite().setTranslateY(-1450 - (playerShots * 32) - (timesSnapped * 9000));
					lasers.add(laser);
					CompetetiveGame.getSwitchBox().getChildren().add(laser.getSprite());
					playerShots++;
					frameLastShot = 0;
				}
				System.out.println(frameLastShot+"----------------");
				break;
				
				
				
				
			case LEFT:
				CompetetiveGame.getPlayer(2).setSpeed(-5);
				playerMoving = true;
				break;
			case RIGHT:
				CompetetiveGame.getPlayer(2).setSpeed(5);
				playerMoving = true;
				break;
			case UP:
				if (frameLastShot2 > 110) {
					int[] pos = { (int) CompetetiveGame.getPlayer(2).getSprite().getTranslateX(), 0 };
					Laser laser = new Laser(1, LaserType.NORMAL,
							new Sprite(pos[0] + 14, 0, "laser2", "PlayerLaser", 4, 32, 8));
					laser.getSprite().setTranslateY(-2120 - (playerShots * 32) - (timesSnapped * 9000));
					lasers.add(laser);
					CompetetiveGame.getSwitchBox().getChildren().add(laser.getSprite());
					playerShots++;
					frameLastShot2 = 0;
				}
				System.out.println(frameLastShot2+"----------------");
				break;
				
				
				
				
				
			case ESCAPE:
				if(gameRunning = true) {
					CompetetiveGame.getTimer().stop();
				}
				break;
//			case L:
//				if (CoOpGame.getLives().size() > 0) {
//					CoOpGame.removeLife();
//				}
			default:
				break;
			}
		});
		scene.setOnKeyReleased((e -> {
			switch (e.getCode()) {
			case A:
				CompetetiveGame.getPlayer(1).setSpeed(0);
				playerMoving = false;
				break;
			case D:
				CompetetiveGame.getPlayer(1).setSpeed(0);
				playerMoving = false;
				break;
			case SPACE:
				break;
				
				
			case LEFT:
				CompetetiveGame.getPlayer(2).setSpeed(0);
				playerMoving = false;
				break;
			case RIGHT:
				CompetetiveGame.getPlayer(2).setSpeed(0);
				playerMoving = false;
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
		CompetetiveController.enemySpeed = enemySpeed;
	}

	public static void reset() {
		frame = 0;
		enemySpeed = 10;
		playerMoving = false;
		moveRight = true;
		shootRow=-1280;
		playerShots = 0;
		frameLastShot = 120;
		frameLastShot2 = 120;
		howFarOffScreen = 0;
		gameOver=false;

	}
}
