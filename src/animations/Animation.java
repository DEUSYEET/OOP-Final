package animations;

import javafx.scene.image.Image;

public class Animation {
	int frameCount = 8;
	Image frames[] = new Image[frameCount];
	Image currentFrame;
	public int frameNum = 0;
	
	public Animation(String file, int W, int H) {
		loadFrames(file, W, H);
		currentFrame = frames[frameNum];
	}

	private void loadFrames(String file, int W, int H) {
		Frame.loadSprite(file);
		for (int i = 1; i<frameCount; i++) {
			frames[i-1] = Frame.getSprite(i,W,H);
		}
		frames[7] = Frame.getSprite(8,W,H);
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
