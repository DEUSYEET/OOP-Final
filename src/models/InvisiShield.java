package models;

public class InvisiShield extends PowerUp{

	@Override
	public void onUse(Player player) {
		player.setCurrentPowerUp(this);
	}

}
