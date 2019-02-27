package models;

public class DoubleTap extends PowerUp{

	public DoubleTap(int x, int y) {
		int[] pos = {x,y};
		
		setPosition(pos);
		this.type = PowerUps.DOUBLETAP;
		
	}
	
	@Override
	public void onUse(Player player) {
		player.setCurrentPowerUp(this);
	}

}
