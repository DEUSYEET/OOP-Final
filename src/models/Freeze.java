package models;

import enums.PowerUps;

public class Freeze extends PowerUp{

	public Freeze(int x, int y) {
		int[] pos = {x,y};
		
		setPosition(pos);
		this.type = PowerUps.FREEZE;
		
	}
	
	@Override
	public void onUse(Player player) {
		player.setCurrentPowerUp(this);
	}

}