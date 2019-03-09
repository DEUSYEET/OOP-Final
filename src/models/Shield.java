package models;

public class Shield {
	
	private int shieldNumber;
	private Sprite sprite;
	
	public Shield(int shieldNumber,int x, int y, String type, String SpriteFile, int W, int H, int frameCount ) {
		
		setShieldNumber(shieldNumber);
		sprite = new Sprite(x, y, type, SpriteFile, W, H, frameCount);
		
	}
	
	public int getShieldNumber() {
		return shieldNumber;
	}
	public void setShieldNumber(int shieldNumber) {
		this.shieldNumber = shieldNumber;
	}
	public Sprite getSprite() {
		return sprite;
	}

}
