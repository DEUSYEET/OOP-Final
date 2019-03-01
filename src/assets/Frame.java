package assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class Frame {
	private static BufferedImage sheet=null;
	private static final int size = 32;
	
    public static BufferedImage loadSprite(String file) {

        try {
            sheet = ImageIO.read(new File("assets/" + file + ".png"));
        } catch (IOException e) {
        	try {
				sheet = ImageIO.read(new File("assets/Null.png"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }

        return sheet;
    }
	public static Image getSprite(int index) {
		
		if (sheet == null) {
			loadSprite("Null");
		}
		BufferedImage sub = sheet.getSubimage(0,size*(index-1),size,size);
		Image sprite = SwingFXUtils.toFXImage(sub, null);
		
		return  sprite;
	}
    
    
    
    
}
