package assets;

import javafx.scene.image.Image;

public class Animation {
	int frameCount = 8;
	Image frames[] = new Image[frameCount];
	Image currentFrame;
	public int frameNum = 0;
	
	
	public Animation(String file) {
		loadFrames(file);
		currentFrame = frames[frameNum];
	}

	private void loadFrames(String file) {
		Frame.loadSprite(file);
		for (int i = 0; i<frameCount-1; i++) {
			frames[i] = Frame.getSprite(i);
		}
	}

	public Image[] getFrames() {
		return frames;
	}

	public Image getCurrentFrame() {
		return currentFrame;
	}

	public void nextFrame() {
		if (frameNum>=frameCount-1) {
			frameNum = 0;
		}
		frameNum++;
		currentFrame = frames[frameNum];
	}
	
	
	
	
	
	
	
	
}
