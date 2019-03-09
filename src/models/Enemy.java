package models;

public class Enemy {
	
	private Sprite sprite;
	private int[] position = new int[2];
	
	public Enemy(int x, int y, String type, String SpriteFile, int W, int H, int frameCount) {
		
		sprite = new Sprite(x, y, type, SpriteFile, W, H, frameCount);
		setXPosition(x);
		setYPosition(y);
		
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getXPosition() {
		return position[0];
	}
	
	public int getYPosition() {
		return position[1];
	}
	
	public void setXPosition(int x) {
		getSprite().setTranslateX(x);
		position[0] = x;
	}
	
	public void setYPosition(int y) {
		getSprite().setTranslateY(y);
		position[1] = y;
	}

}
