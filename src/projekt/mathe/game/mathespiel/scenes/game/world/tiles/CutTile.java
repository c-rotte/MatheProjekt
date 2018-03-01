package projekt.mathe.game.mathespiel.scenes.game.world.tiles;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Logger;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class CutTile extends Tile{

	public static void addTiles(Scene container, World world, int x, int y, boolean belowPlayer, Image bigImage) {
		int w = bigImage.getWidth(null);
		int h = bigImage.getHeight(null);
		if(w % Values.TILE_SIZE != 0 || h % Values.TILE_SIZE != 0) {
			Logger.log("Wrong Image size: " + w + " x " + h);
			return;
		}
		int rows = w / Values.TILE_SIZE;
		int coloums = h / Values.TILE_SIZE;
		try {
			for(int r = 0; r < rows; r++) {
				for(int c = 0; c < coloums; c++) {
					BufferedImage t = Helper.cutPart(bigImage, Values.TILE_SIZE * r, Values.TILE_SIZE * c, Values.TILE_SIZE, Values.TILE_SIZE);
					if(!isImageEmpty(t)) {
						world.addTile(new CutTile(container, world, x + r * Values.TILE_SIZE, y + c * Values.TILE_SIZE, belowPlayer, t));
					}
				}
			}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static void addSplicedTiles(Scene container, World world, int x, int y, boolean belowPlayer, Image[] images, int rows) {
		int c = 0;
		int r = 0;
		for(int i = 0; i < images.length; i++) {
			if(r % rows == 0 && r != 0) {
				r = 0;
				c++;
			}
			if(images[i] != null) {
				addTiles(container, world, x + images[i].getWidth(null) * r, y + c * images[i].getHeight(null), belowPlayer, images[i]);
			}
			r++;
		}
	}
	
	public static boolean isImageEmpty(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		for(int w = 0; w < width; w++) {
			for(int h = 0; h < height; h++) {
				int pixelColor = image.getRGB(w, h);
				Color c = new Color(pixelColor, true);
				if(c.getAlpha() != 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public CutTile(Scene container, World world, int x, int y, boolean belowPlayer, Image texture) {
		super(container, world, x, y, Values.TILE_SIZE, Values.TILE_SIZE, belowPlayer);
		Image[] i = {texture};
		addTextures("normal", i, 10);
	}

}
