package animations;

import javafx.scene.image.Image;

public class Animation {
	int frameCount;
	Image frames[];
	Image currentFrame;
	public int frameNum = 0;

	public Animation(String file, int W, int H, int frameCount) {
		this.frameCount = frameCount;
		frames = new Image[frameCount];
		loadFrames(file, W, H);
		currentFrame = frames[frameNum];
	}

	private void loadFrames(String file, int W, int H) {
		Frame.loadSprite(file);
		if (frameCount > 1) {
			for (int i = 1; i < frameCount; i++) {
				frames[i - 1] = Frame.getSprite(i, W, H);
			}
			frames[7] = Frame.getSprite(8, W, H);
		} else {
			frames[0] = Frame.getSprite(1, W, H);
		}
	}

	public Image[] getFrames() {
		return frames;
	}

	public Image getCurrentFrame() {
		return currentFrame;
	}

	public void nextFrame() {
		if (frameCount > 1) {
			if (frameNum >= frameCount - 1) {
				frameNum = 0;
			}
			frameNum++;
			currentFrame = frames[frameNum];
		}
	}

}
