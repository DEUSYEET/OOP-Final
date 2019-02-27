package models;

import enums.PowerUps;

public abstract class PowerUp {
	
	protected int[] position;
	protected PowerUps type;
	
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
	
	public PowerUps getType() {
		return type;
	}
	
	public abstract void onUse(Player player);

}
