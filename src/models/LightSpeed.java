package models;

public class LightSpeed extends PowerUp{

	@Override
	public void onUse(Player player) {
		player.setCurrentPowerUp(this);
	}

}
