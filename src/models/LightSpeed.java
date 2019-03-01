package models;

import enums.PowerUps;

public class LightSpeed extends PowerUp {

	public LightSpeed(int x, int y) {
		int[] pos = { x, y };

		setPosition(pos);
		this.type = PowerUps.LIGHTSPEED;

	}

	@Override
	public void onUse(Player player) {
		player.setCurrentPowerUp(this);
	}

}
