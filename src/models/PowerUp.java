package models;

public abstract class PowerUp {
	
	protected int[] position;
	
	public abstract void onUse(Player player);

}
