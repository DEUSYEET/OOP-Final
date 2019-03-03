package animations;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Frame {
	private static BufferedImage sheet=null;
	
    public static BufferedImage loadSprite(String file) {

        try {
            sheet = ImageIO.read(new File("src/assets/" + file + ".png"));
        } catch (IOException e) {
        	try {
				sheet = ImageIO.read(new File("src/assets/Null.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }

        return sheet;
    }
	public static Image getSprite(int index, int w, int h) {
		
		
		if (sheet == null) {
			loadSprite("Null");
		}
		BufferedImage sub = sheet.getSubimage(0,h*(index-1),w,h);
		Image sprite = SwingFXUtils.toFXImage(sub, null);
		
		return  sprite;
	}
    
    
    
    
}
