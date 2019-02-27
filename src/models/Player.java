package models;

public class Player {

	private int playerNumber;
	private int lives;
	private PowerUp currentPowerUp;
	private int score;
	private int[] position;
	private boolean isAlive;

	public Player() {

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
