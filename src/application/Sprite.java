package application;

import animations.Animation;
import javafx.scene.image.ImageView;

public class Sprite extends ImageView {
	private boolean oofed = false;
	private final String type;
	private Animation animation;
	private int W;
	private int H;
	private String spriteFile;
	private int frameCount;
	

	public Sprite(int x, int y, String type, String SpriteFile, int W, int H, int frameCount) {
		this.type = type;
		setW(W);
		setH(H);
		this.frameCount = frameCount;
		spriteFile = SpriteFile;
		createAnimation();
		setTranslateX(x);
		setTranslateY(y);
		setImage(animation.getCurrentFrame());
	}

	private void createAnimation() {
		Animation a = new Animation(spriteFile, W, H, frameCount);
		animation = a;

	}

	public boolean isOofed() {
		return oofed;
	}

	public void setOofed(boolean oofed) {
		this.oofed = oofed;
	}

	public Animation getAnimation() {
		return animation;
	}

	public void setAnimation(Animation animation) {
		this.animation = animation;
	}

	public String getType() {
		return type;
	}

	public void update() {
		animation.nextFrame();
		setImage(animation.getCurrentFrame());
	}

	public void moveLeft() {
		setTranslateX(getTranslateX() - 5);
	}

	public void moveRight() {
		setTranslateX(getTranslateX() + 5);
	}

	public void moveUp() {
		setTranslateY(getTranslateY() - 5);
	}

	public void moveDown() {
		setTranslateY(getTranslateY() + 5);
	}

	public int getW() {
		return W;
	}

	public void setW(int w) {
		W = w;
	}

	public int getH() {
		return H;
	}

	public void setH(int h) {
		H = h;
	}
}
