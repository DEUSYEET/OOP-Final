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
	private int currentFrame=1;
	private int howLongBeenOofed = -1;
	private int zoom = 5;

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
	
	public void setSpriteFile(String spriteFile) {
		this.spriteFile = spriteFile;
		createAnimation();
		setImage(animation.getCurrentFrame());
	}

	public String getSpriteFile() {
		return spriteFile;
	}

	private void createAnimation() {
		Animation a = new Animation(spriteFile, W, H, frameCount);
		animation = a;

	}

	public boolean isOofed() {
		return oofed;
	}
	
	public void updateHowLongBeenOofed() {
		howLongBeenOofed++;
	}
	
	public int getHLBO() {
		return howLongBeenOofed;
	}
	
	public void setHLBO(int hlbo) {
		this.howLongBeenOofed = hlbo;
	}

	public void setOofed(boolean oofed) {
		setHLBO(-1);
		updateHowLongBeenOofed();
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
//		if (currentFrame>=frameCount) {
//			currentFrame=0;
//		}else {
			currentFrame++;
//		}
	}

	public void moveLeft() {
		setTranslateX(getTranslateX() - zoom);
	}

	public void moveRight() {
		setTranslateX(getTranslateX() + zoom);
	}

	public void moveUp() {
		setTranslateY(getTranslateY() - zoom);
	}

	public void moveDown() {
		setTranslateY(getTranslateY() + zoom);
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

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}
	
}
