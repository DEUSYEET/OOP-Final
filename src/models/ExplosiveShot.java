package models;

public class ExplosiveShot extends PowerUp{

	public ExplosiveShot(int x, int y) {
		int[] pos = {x,y};
		
		setPosition(pos);
		this.type = PowerUps.EXPLOSIVESHOT;
		
	}
	
	@Override
	public void onUse(Player player) {
		player.setCurrentPowerUp(this);
	}

}
