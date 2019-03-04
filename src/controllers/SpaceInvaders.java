package controllers;

import java.util.ArrayList;

import application.Main;
import application.Sprite;
import enums.GameModes;
import enums.LaserType;
import javafx.scene.Scene;
import models.Laser;
import view.SinglePlayer;

public class SpaceInvaders {
	
	public boolean gameRunning = false;
	private static int frame = 0;
	private static int enemySpeed = 100;
	private static boolean playerMoving = false;
	private static boolean moveRight = true;
	private static ArrayList<Laser> lasers = new ArrayList<Laser>();
	private static int playerShots = 0;
	private static int frameLastShot = 120;

	public void gameLoop() {
		
		while (gameRunning) {
			update();
		}

	}

	public static void update() {
		
		SinglePlayer.setT(SinglePlayer.getT() + 1);
		if (SinglePlayer.getT() > 2) {
			for (Sprite s : SinglePlayer.getSprites()) {
				s.update();
			}

			SinglePlayer.setT(0);
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
		
		if (SinglePlayer.getEnemies().get(39).getTranslateX() > 570) {
			
			for (Sprite e : SinglePlayer.getEnemies()) {
				e.moveDown();
				e.moveLeft();
			}
			moveRight = false;
			
		}
		if (SinglePlayer.getEnemies().get(0).getTranslateX() < 0) {
			
			for (Sprite e : SinglePlayer.getEnemies()) {
				e.moveDown();
				e.moveRight();
			}
			moveRight = true;
			
		}
		
		for (Laser l: lasers) {
			if (frame % l.getSpeed() == 0) {
				l.getSprite().moveUp();
			}
		}
		
		checkIfLaserTouchesAnything();
		
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
	
	private static void checkIfLaserTouchesAnything() {
		
		ArrayList<Laser> offed = new ArrayList<Laser>();
		
		for (Laser l : lasers) {
			for (Sprite s : SinglePlayer.getSprites()) {
				
				if (l.getSprite().getBoundsInParent().intersects(s.getBoundsInParent()) && !l.getSprite().equals(s)) {
					// kaboom
					System.out.println("hit");
					frameLastShot = 120;
					offed.add(l);
					
				}
				
			}
		}
		for (Laser l : offed) {
			l.getSprite().setTranslateY(42069);
			lasers.remove(l);
			SinglePlayer.getSprites().remove(l.getSprite());
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
					Laser laser = new Laser(pos, LaserType.NORMAL, 1, new Sprite(pos[0], 0, "laser", "PlayerLaser", 32, 32, 8));
					laser.getSprite().setTranslateY(-1090 - (playerShots * 32));
					lasers.add(laser);
					SinglePlayer.getSwitchBox().getChildren().add(laser.getSprite());
					playerShots++;
					System.out.println(frameLastShot);
					frameLastShot = 0;
				}
				break;
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
