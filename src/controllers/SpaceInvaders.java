
package controllers;

import java.util.ArrayList;

import application.Main;
import application.Sprite;
import enums.LaserType;
import javafx.scene.Node;
import javafx.scene.Scene;
import models.Laser;
import view.GameOverMenu;
import view.MainMenu;
import view.Pause;
import view.SinglePlayer;

public class SpaceInvaders {

	public static boolean gameRunning = false, gameOver = false;
	public static int countToBottom = 0;
	public static int timesSnapped = 0;
	private static int frame = 0;
	private static int enemySpeed = 100;
	private static boolean playerMoving = false;
	private static boolean moveRight = true;
	private static ArrayList<Laser> lasers = new ArrayList<Laser>();
	private static int playerShots = 0;
	private static int frameLastShot = 120;
	private static int howFarOffScreen = 0;

	public static void update() {
		if (gameRunning) {
			SinglePlayer.setT(SinglePlayer.getT() + 1);
			if (SinglePlayer.getT() > 2) {
				for (Sprite s : SinglePlayer.getSprites()) {
					if (s.isOofed()) {
						s.updateHowLongBeenOofed();
					}
					if (s.getHLBO() < 1 || (!s.isOofed())) {
						// System.out.println(s.getType());
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
						// System.out.println(s.getHLBO());
						s.setTranslateY(42069);
						SinglePlayer.getSprites().remove(s);

					} else {
						s.setSpriteFile("explosion");
						if (!s.getType().equals("player")) {
							SinglePlayer.addScore(10);
						}
						System.out.println(SinglePlayer.getScore());
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
//					System.out.println(frameLastShot);
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

			for (Sprite e : SinglePlayer.getEnemies()) {
				
				if (!e.isOofed() && (e.getTranslateX() < 0 || e.getTranslateX() > 570)) {
					moveRight = !moveRight;
					System.out.println(e.getTranslateX());
					if (countToBottom <= 275000 && countToBottom >= 17817) {
						gameOver = true;
						setEnemySpeed(Integer.MAX_VALUE - 1);
					}
					
					if (e.getTranslateX() < 0) {
						for (Sprite es : SinglePlayer.getEnemies()) {
							es.moveDown();
							es.moveRight();
							
						}

					} else {
						for (Sprite es : SinglePlayer.getEnemies()) {
							es.moveDown();
							es.moveLeft();
						}
					}
					break;
				}
			}
			for (Laser l : lasers) {
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
			
			countToBottom++;
			frame++;
			frameLastShot += 20;
		}
		if (gameOver) {
			System.out.println("GAME OVER");
			gameOver = false;
			SinglePlayer.snap();
			SinglePlayer.getEnemies().clear();
			MainMenu.getStage().setScene(GameOverMenu.getScene(MainMenu.getStage()));
		}
	}

	private static void checkIfLaserTouchesAnything() {

		ArrayList<Laser> offed = new ArrayList<Laser>();
		ArrayList<Sprite> kaboomed = new ArrayList<Sprite>();

		for (Laser l : lasers) {
			for (Sprite s : SinglePlayer.getSprites()) {

				if (l.getSprite().getBoundsInParent().intersects(s.getBoundsInParent()) && !l.getSprite().equals(s)
						&& !s.isOofed()) {
					// kaboom
					if (s.getType().equals("shield")) {
						System.out.println(s.getCurrentFrame());
						if (s.getCurrentFrame() <= 8) {
							s.update();
							System.out.println("hit");
							frameLastShot = 120;
							offed.add(l);
							howFarOffScreen++;
						} 

					} else {
						kaboomed.add(s);
						System.out.println("hit");
						frameLastShot = 120;
						offed.add(l);
						howFarOffScreen++;
					}

				}
				if (l.getSprite().getTranslateY() < -1500 - (32 * howFarOffScreen)) {
					System.out.println("Off");
					offed.add(l);
					howFarOffScreen++;
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
					System.out.println(frameLastShot);
					frameLastShot = 0;
				}
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

	public static int getEnemySpeed() {
		return enemySpeed;
	}

	public static void setEnemySpeed(int enemySpeed) {
		SpaceInvaders.enemySpeed = enemySpeed;
	}

	public static void startApp(String[] args) {

		Main.main(args);

	}
}