package application;

import animations.Animation;
import javafx.scene.image.ImageView;

class Sprite extends ImageView{
	private boolean oofed = false;
	private final String type;
	private Animation animation;
	
	Sprite(int x, int y, String type, String SpriteFile) {
		this.type = type;
		setAnimation(new Animation(SpriteFile));
		setTranslateX(x);
		setTranslateY(y);
		setImage(animation.getCurrentFrame());
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
}
