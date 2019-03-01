package application;

import assets.Animation;
import javafx.scene.image.ImageView;

class Sprite extends ImageView{
	boolean oofed = false;
	final String type;
	Animation animation;
	
	Sprite(int x, int y, String type, String SpriteFile) {
		super();
		this.type = type;
		animation = new Animation(SpriteFile);
		setTranslateX(x);
		setTranslateY(y);
		setImage(animation.getCurrentFrame());
	}



	public void update() {
		animation.nextFrame();
		setImage(animation.getCurrentFrame());
	}
	
	void moveLeft() {
		setTranslateX(getTranslateX() - 5);
	}

	void moveRight() {
		setTranslateX(getTranslateX() + 5);
	}

	void moveUp() {
		setTranslateY(getTranslateY() - 5);
	}

	void moveDown() {
		setTranslateY(getTranslateY() + 5);
	}
}
