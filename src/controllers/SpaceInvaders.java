
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
import view.Pause;
import view.SinglePlayer;

public class SpaceInvaders {

	public static boolean gameRunning = false, gameOver = false;
	private static int frame = 0;
	private static int enemySpeed = 100;
	private static boolean playerMoving = false;
	private static boolean moveRight = true;
	private static ArrayList<Laser> lasers = new ArrayList<Laser>();
	private static int playerShots = 0;
	private static int frameLastShot = 120;
	private static int howFarOffScreen = 0;
	private static int shootRow=-1280;
	private static Random rng = new Random();
	public static int timesSnapped = 0;

	public static void update() {
		if (gameRunning) {
			SinglePlayer.setT(SinglePlayer.getT() + 1);
			if (SinglePlayer.getT() > 2) {
				for (Sprite s : SinglePlayer.getSprites()) {
					if (s.isOofed()) {
						s.updateHowLongBeenOofed();
					}
					if (s.getType().equals("enemy")) {
						int rand = rng.nextInt(100)% 10;
//								* rng.nextInt(100 * SinglePlayer.getEnemies().size()) % 69;
						//System.out.println(rand);
						if (rand == 0) {
							//System.out.println("shoot");
							if (SinglePlayer.getEnemies().size() > 20) {
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
						// //system.out.println(s.getType());
						if (s.getType().equals("shield")) {
						} else {
							s.update();
						}
					}

					else if (s.getHLBO() > 10 && s.isOofed()) {
						// System.out.println(s.getHLBO());
						if (s.getType().equals("player")) {
							s.setSpriteFile("idle");
							s.setH(24);
							s.setW(32);
							s.setOofed(false);
							s.setHLBO(-1);
							continue;
						}
						// //system.out.println(s.getHLBO());
						s.setTranslateY(42069);
						SinglePlayer.getSprites().remove(s);

					} else {
						s.setSpriteFile("explosion");
						if (!s.getType().equals("player")) {
							SinglePlayer.addScore(10);
						}
						// system.out.println(SinglePlayer.getScore());
						s.setH(32);
						s.setW(16);
					}
				}

				SinglePlayer.setT(0);
			}

			if (SinglePlayer.getLives().size() == 0) {
				gameRunning = false;
				gameOver = true;
			}

			controls(SinglePlayer.getScene());
			SinglePlayer.getPlayer().movePlayer();
			if (playerMoving) {
				enemySpeed -= 20;
			}

			if (frame % enemySpeed == 0) {
				// make the enemy shoot not the player
				int[] pos = { (int) SinglePlayer.getPlayer().getSprite().getTranslateX(), 0 };
//				Laser laser = new Laser(pos, 1,LaserType.ALIEN, new Sprite(pos[0] + 14, 0, "laser", "EnemyLaser", 4, 32, 8));
//				laser.getSprite().setTranslateY(-980 - (playerShots * 32));
//				lasers.add(laser);
//				SinglePlayer.getSwitchBox().getChildren().add(laser.getSprite());
				if (moveRight) {
					// system.out.println(frameLastShot);
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

				if (!e.isOofed() && (e.getTranslateX() < 0 || e.getTranslateX() > 570)) {
					moveRight = !moveRight;
					System.out.println(e.getTranslateX());
					if (e.getTranslateY() < 0) {
						gameOver = true;
					}
					if (e.getTranslateX() < 0) {
						for (Sprite es : SinglePlayer.getEnemies()) {
							es.moveDown();
							es.moveRight();
							if (down) {
								shootRow+=10;
								down=!down;
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

			checkIfLaserTouchesAnything();

			if (SinglePlayer.getEnemies().isEmpty()) {
				SinglePlayer.populateEnemies();
			}

			frame++;
			frameLastShot++;
		}
		if (gameOver) {
			System.out.println("GAME OVER");
			gameOver = false;
			SinglePlayer.snap();

			MainMenu.getStage().setScene(GameOverMenu.getScene(MainMenu.getStage()));
		}
	}

	// static int shoot = 0;
	private static int enemyShots;

	private static void shoot(Sprite s) {
		int rand = rng.nextInt(100) + 1;
		int[] pos = { (int) s.getTranslateX(), (int) s.getTranslateY() };

		// if (shoot > 500) {
//			Sprite es = new Sprite((int) s.getTranslateX(), (int) s.getTranslateY() + 20, "eLaser", "test", 64, 32, 1);
//			//system.out.println("shot");
//			Laser laser = new Laser(1, LaserType.ALIEN,
//					es);
//			playerShots++;
//			lasers.add(laser);
		// shoot = 0;
//			SinglePlayer.getSwitchBox().getChildren().add(es);
//			laser.getSprite().setTranslateY(-s.getTranslateY() - (playerShots * 32));
//			//system.out.println(s.getTranslateY());

		Laser laser = new Laser(1, LaserType.ALIEN, new Sprite(pos[0] + 14, 0, "laser", "EnemyLaser", 4, 32, 8));
		laser.getSprite().setTranslateY(shootRow - (playerShots * 32));
		lasers.add(laser);
		SinglePlayer.getSwitchBox().getChildren().add(laser.getSprite());
		playerShots++;
		// system.out.println(frameLastShot);
		// frameLastShot = 0;
//		} else {
//			shoot++;
//		}

	}

	private static void checkIfLaserTouchesAnything() {

		ArrayList<Laser> offed = new ArrayList<Laser>();
		ArrayList<Sprite> kaboomed = new ArrayList<Sprite>();

		for (Laser l : lasers) {
			//System.out.println(l.getTimeAlive());
			if (l.getTimeAlive()>300) {
				offed.add(l);
				System.out.println("Removed Laser");
			}
			for (Sprite s : SinglePlayer.getSprites()) {
				if (s.getType().equals("enemy") && l.getType() == LaserType.ALIEN
						&& l.getSprite().getBoundsInParent().intersects(s.getBoundsInParent())
						&& !l.getSprite().equals(s)) {
					// system.out.println("------------------");
				} else if (l.getSprite().getBoundsInParent().intersects(s.getBoundsInParent())
						&& !l.getSprite().equals(s) && !s.isOofed()) {
					// kaboom

					if (s.getType().equals("shield")) {
						// system.out.println(s.getCurrentFrame());
						if (s.getCurrentFrame() <= 8) {
							s.update();
							// system.out.println("hit");
							frameLastShot = 120;
							offed.add(l);
							howFarOffScreen++;
						} else {

						}

					} else {
						kaboomed.add(s);
						// system.out.println("hit");
						frameLastShot = 120;
						offed.add(l);
						howFarOffScreen++;
					}

				}
//				if (l.getSprite().getTranslateY() < -1500 - (32 * howFarOffScreen)) {
//					// system.out.println("Off");
//					offed.add(l);
//					howFarOffScreen++;
//				}

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

//	private static void checkShield(Sprite s) {
//		int frame = s.getCurrentFrame();
//		if (frame == 8) {
//			SinglePlayer.getSwitchBox().getChildren().remove(s);
//
//		}
//
//	}

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
					Laser laser = new Laser(pos, 1, LaserType.NORMAL,
							new Sprite(pos[0] + 14, 0, "laser", "PlayerLaser", 4, 32, 8));
					laser.getSprite().setTranslateY(-980 - (playerShots * 32) - (timesSnapped * 9000));
					lasers.add(laser);
					SinglePlayer.getSwitchBox().getChildren().add(laser.getSprite());
					playerShots++;
					// system.out.println(frameLastShot);
					frameLastShot = 0;
				}
				System.out.println(frameLastShot+"----------------");
				break;
			case ESCAPE:
				gameRunning = false;
				MainMenu.getStage().setScene(Pause.getScene(MainMenu.getStage()));
				break;
			case L:
				if (SinglePlayer.getLives().size() > 0) {
					SinglePlayer.removeLife();
				}
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

	public static void reset() {
		frame = 0;
		enemySpeed = 50;
		playerMoving = false;
		moveRight = true;
		shootRow=-1280;
		playerShots = 0;
		frameLastShot = 120;
		howFarOffScreen = 0;

	}
}
