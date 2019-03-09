package models;

import animations.Animation;
import javafx.scene.image.ImageView;

public class Sprite extends ImageView{
	
	private String type;
	private Animation animation;
	private int W;
	private int H;
	private String spriteFile;
	private int frameCount;
	private int currentFrame=1;
	
	public Sprite(int x, int y, String type, String SpriteFile, int W, int H, int frameCount) {
		this.type = type;
		this.W = W;
		this.H = H;
		this.frameCount = frameCount;
		spriteFile = SpriteFile;
		animation = new Animation(spriteFile, W, H, frameCount);
		setTranslateX(x);
		setTranslateY(y);
		setImage(animation.getCurrentFrame());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Animation getAnimation() {
		return animation;
	}

	public int getW() {
		return W;
	}

	public int getH() {
		return H;
	}

	public String getSpriteFile() {
		return spriteFile;
	}
	
	public void setSpriteFile(String spriteFile) {
		
		this.spriteFile = spriteFile;
		animation = new Animation(spriteFile, W, H, frameCount);
		
	}

	public int getFrameCount() {
		return frameCount;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	
	public void update() {
		animation.nextFrame();
		setImage(animation.getCurrentFrame());
		currentFrame++;
	}

}
