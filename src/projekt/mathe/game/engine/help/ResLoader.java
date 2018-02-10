package projekt.mathe.game.engine.help;

import java.awt.Image;
import java.io.InputStream;

import javax.swing.ImageIcon;

public class ResLoader {
	
	public static Image getImageByName(String name){
		ImageIcon image = null;
		try {
			image = new ImageIcon(ResLoader.class.getResource("/res/" + name));
		} catch (Exception e) {
			image = new ImageIcon(ResLoader.class.getResource("/" + name));
		}
		Logger.notifyLoading(name);
		return Helper.optimizeImage(image.getImage());
	}

	public static InputStream getFile(String name) {
		InputStream inputStream = ResLoader.class.getResourceAsStream("/res/" + name);
		if(inputStream == null) {
			inputStream = ResLoader.class.getResourceAsStream("/" + name);
		}
		Logger.notifyLoading(name);
		return inputStream;
	}

}
