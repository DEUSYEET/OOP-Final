package controllers;

import javafx.animation.AnimationTimer;
import models.Enemy;
import models.GameMode;
import models.Sprite;

public class SpaceInvaders{
	
	public boolean gameRunning = false;
	public int frame = 0;
	public GameMode gameType;
	public int enemySpeed = 100;
	public int enemyMoveDistance = 5;
	public boolean moveRight = true;
	private AnimationTimer timer = new AnimationTimer() {
		@Override 
		public void handle(long now) {
			if (gameRunning) {
				update(gameType);
			}
			
			gameType.getScene().setOnKeyPressed(e ->{
				switch (e.getCode()) {
				case D:
					if (gameRunning) {
						gameType.getPlayer().setSpeed(5);
					}
					
					break;
				case A:
					if (gameRunning) {
						gameType.getPlayer().setSpeed(-5);
					}
					break;
				default:
					break;
				case ESCAPE:
					gameRunning = !gameRunning;
					System.out.println("hit");

				}
			});
			gameType.getScene().setOnKeyReleased(e ->{
				switch (e.getCode()) {
				case D:
					if (gameRunning) {
						gameType.getPlayer().setSpeed(0);
					}
					break;
				case A:
					if (gameRunning) {
						gameType.getPlayer().setSpeed(0);
					}
					break;
				default:
					break;
				}
			});
		}
	};
	
	public SpaceInvaders(GameMode gameType) {
		this.gameType = gameType;
		timer.start();
	}

	private void update(GameMode whoCalled) {
		if (frame % 3 == 0) {
			for (Sprite s : whoCalled.getSprites()) {
				
				if (s.getType().equals("shield")) {
				}
				else {
					s.update();
				}
				
			}
		}
		gameType.getPlayer().movePlayer();
		moveEnemies();
		frame++; 
		
	}

	private void moveEnemies() {
		boolean moveDown = false;
		if (frame % enemySpeed == 0) {
			
			for (Enemy e : gameType.getEnemies()) {
				if (e.getXPosition() > 570 || e.getXPosition() < 0) {
					System.out.println(moveRight);
					moveDown = true;
				}
			}
			if (moveDown) {
				moveRight = !moveRight;
			}
			for (Enemy e : gameType.getEnemies()) {
				if (moveDown) {
					e.setYPosition(e.getYPosition() + 5);
				}
				if (moveRight) {
					e.setXPosition(e.getXPosition() + enemyMoveDistance);
				}
				else {
					e.setXPosition(e.getXPosition() - enemyMoveDistance);
				}
				
			}
			
			
		}
		
	}
	
	
}



