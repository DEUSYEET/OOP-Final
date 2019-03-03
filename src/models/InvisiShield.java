package models;

import enums.PowerUps;

public class InvisiShield extends PowerUp {

	public InvisiShield(int x, int y) {
		int[] pos = { x, y };

		setPosition(pos);
		this.type = PowerUps.INVISISHIELD;

	}

	@Override
	public void onUse(Player player) {
		player.setCurrentPowerUp(this);
	}

}
