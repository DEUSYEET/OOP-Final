package models;

import application.Sprite;
import enums.LaserType;

public class Laser {
	
	private LaserType type;
//	private int[] position;
	private int speed;
	private Sprite sprite;
	private int timeAlive=0;
	
	public Laser(int speed,LaserType type, Sprite sprite) {
		
		setType(type);
	//	setPosition(pos);
		setSpeed(speed);
		this.sprite = sprite;
		
	}
	
	public LaserType getType() {
		return type;
	}
	public void setType(LaserType type) {
		this.type = type;
	}
//	public int[] getPosition() {
//		return position;
//	}
//	public void setPosition(int[] position) {
//		if (position.length != 2) {
//			throw new IllegalArgumentException("position can only have 2 values in it ya forehead");
//		}
//		if (position[0] < 0 || position[1] < 0) {
//			throw new IllegalArgumentException("our board does not have positions less than 0 boi");
//		}
//		
//		this.position = position;
//	}
	
	public Sprite getSprite() {
		return sprite;
	}

	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getTimeAlive() {
		return timeAlive;
	}

	public void setTimeAlive(int timeAlive) {
		this.timeAlive += timeAlive;
	}
	
	
	
}
