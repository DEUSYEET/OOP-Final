package controllers;

import java.util.ArrayList;

import application.Main;
import application.Sprite;
import javafx.scene.Scene;
import models.Laser;
import view.GameOverMenu;
import view.MainMenu;
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

	public static void update() {
		if (gameRunning) {
			SinglePlayer.setT(SinglePlayer.getT() + 1);
			if (SinglePlayer.getT() > 2) {
				for (Sprite s : SinglePlayer.getSprites()) {
					if (s.isOofed()) {
						s.updateHowLongBeenOofed();
					}
					if (s.getHLBO() < 1 || (!s.isOofed())) {					
						s.update();
					}
				
					else if (s.getHLBO() > 10 && s.isOofed()) {
						//System.out.println(s.getHLBO());
						s.setTranslateY(42069);
						SinglePlayer.getSprites().remove(s);
			
					}
					else {
						s.setSpriteFile("explosion");
						SinglePlayer.addScore(10);
						System.out.println(SinglePlayer.getScore());
						s.setH(32);
						s.setW(16);
					}
				}
				
				SinglePlayer.setT(0);
			}
			
			if(SinglePlayer.getLives().size() == 0) {
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
				}
				else {
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
					if (e.getTranslateX() < 0) {
						for (Sprite es : SinglePlayer.getEnemies()) {
							es.moveDown();
							es.moveRight();
						}
					}
					else {
						for (Sprite es : SinglePlayer.getEnemies()) {
							es.moveDown();
							es.moveLeft();
						}
					}
					break;
				}
				
			}
			
			for (Laser l: lasers) {
				if (frame % l.getSpeed() == 0) {
					l.getSprite().moveUp();
				}
			}
			
			checkIfLaserTouchesAnything();
			
			if (SinglePlayer.getEnemies().isEmpty()) {
				SinglePlayer.populateEnemies();
			}
			
			//set position of enemies
			
			//set position of player(s)
			
			//set position of laser
			
			//check if anything has been hit by anything
				//if laser hits enemy = dead
				//if laser hits player = lose a life out of 3
				//if laser hits barricade = breaks
				//if enemy hits block = breaks sections that are touched
			
			frame++;
			frameLastShot++;
		}
		if(gameOver){
			System.out.println("GAME OVER");
			gameOver = false;
			MainMenu.getStage().setScene(GameOverMenu.getScene(MainMenu.getStage()));
		}
	}
	
	private static int howFarOffScreen = 0;
	
	private static void checkIfLaserTouchesAnything() {
		
		ArrayList<Laser> offed = new ArrayList<Laser>();
		ArrayList<Sprite> kaboomed = new ArrayList<Sprite>();
		
		for (Laser l : lasers) {
			for (Sprite s : SinglePlayer.getSprites()) {
				
				if (l.getSprite().getBoundsInParent().intersects(s.getBoundsInParent()) && !l.getSprite().equals(s) && !s.isOofed()) {
					// kaboom
					System.out.println("hit");
					frameLastShot = 120;
					offed.add(l);
					kaboomed.add(s);
					howFarOffScreen++;
					
				}
				if ( l.getSprite().getTranslateY() < -1500 - (32 * howFarOffScreen)) {
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
					int[] pos = {(int) SinglePlayer.getPlayer().getSprite().getTranslateX(),0};
					Laser laser = new Laser(pos, 1, new Sprite(pos[0] + 14, 0, "laser", "PlayerLaser", 4, 32, 8));
					laser.getSprite().setTranslateY(-980 - (playerShots * 32));
					lasers.add(laser);
					SinglePlayer.getSwitchBox().getChildren().add(laser.getSprite());
					playerShots++;
					System.out.println(frameLastShot);
					frameLastShot = 0;
				}
				break;
			case ESCAPE:
				gameRunning = false;
				MainMenu.getStage().setScene(MainMenu.getScene(MainMenu.getStage()));
				break;
			case L:
				if(SinglePlayer.getLives().size() > 0) {
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
}
