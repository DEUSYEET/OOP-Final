package models;

import enums.LaserType;

public class Laser {
	
	private LaserType type;
	private int[] position;
	private int speed;
	
	public Laser(int[] pos, LaserType type, int speed) {
		
		setType(type);
		setPosition(pos);
		setSpeed(speed);
		
	}
	
	public LaserType getType() {
		return type;
	}
	public void setType(LaserType type) {
		this.type = type;
	}
	public int[] getPosition() {
		return position;
	}
	public void setPosition(int[] position) {
		if (position.length != 2) {
			throw new IllegalArgumentException("position can only have 2 values in it ya forehead");
		}
		if (position[0] < 0 || position[1] < 0) {
			throw new IllegalArgumentException("our board does not have positions less than 0 boi");
		}
		
		this.position = position;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	
	
}
