package models;

import application.Sprite;

public class Player {

	private int playerNumber;
	private int lives;
	private PowerUp currentPowerUp;
	private int score;
	private int[] position;
	private boolean isAlive;
	private Sprite sprite;
	private int speed;
	private int rightBoundary;
	private int leftBoundary;
	

	public Player(int playerNumber) {

		setPlayerNumber(playerNumber);
		setLives(3);
		setCurrentPowerUp(null);
		setScore(0);
		int[] pos = { 0, 0 };
		setPosition(pos);
		setAlive(true);
		this.sprite = new Sprite(5, 520, "player" + playerNumber, "idle" + playerNumber, 32, 24, 8);
		
		if (playerNumber == 3) {
			setRightBoundary(1170);
			setLeftBoundary(600);
		}
		else {
			setRightBoundary(600);
			setLeftBoundary(0);
		}

	}

	public void movePlayer() {
		
		if (sprite.getTranslateX() >= getRightBoundary()) {
			setSpeed(0);
			sprite.setTranslateX(getRightBoundary() - 1);
		}
		if (sprite.getTranslateX() <= getLeftBoundary()) {
			setSpeed(0);
			sprite.setTranslateX(getLeftBoundary() + 1);
		}

		sprite.setTranslateX(sprite.getTranslateX() + getSpeed());

	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	

	public int getRightBoundary() {
		return rightBoundary;
	}

	public void setRightBoundary(int rightBoundary) {
		this.rightBoundary = rightBoundary;
	}

	public int getLeftBoundary() {
		return leftBoundary;
	}

	public void setLeftBoundary(int leftBoundary) {
		this.leftBoundary = leftBoundary;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public PowerUp getCurrentPowerUp() {
		return currentPowerUp;
	}

	public void setCurrentPowerUp(PowerUp currentPowerUp) {
		this.currentPowerUp = currentPowerUp;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

}
