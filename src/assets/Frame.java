package assets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Frame {
	private static BufferedImage sheet=null;
	private static final int size = 32;
	
    public static BufferedImage loadSprite(String file) {

        try {
            sheet = ImageIO.read(new File("assets/" + file + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sheet;
    }
	public static BufferedImage getSprite(int index) {
		
		if (sheet == null) {
			loadSprite("Null");
		}
		BufferedImage sub = sheet.getSubimage(0,size*index,size,size);
		
		
		return  sub;
	}
    
    
    
    
}
