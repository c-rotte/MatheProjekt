package projekt.mathe.game.mathespiel.scenes.game.world.entities;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class Can extends Entity {

	private static final Image can_top = ResLoader.getImageByName("game/entities/can_top.png");
	private static final Image can_color = ResLoader.getImageByName("game/entities/can_color.png");
	
	private static final Color[] COLORS = {
		Color.RED,
		Color.BLUE,
		Color.CYAN,
		Color.YELLOW,
		Color.GRAY,
		Color.GREEN,
		Color.ORANGE
	};
	
	private BufferedImage generated_color;
	
	private int degrees;
	
	public Can(Scene container, World world, int x, int y) {
		super(container, world, x, y, 26, 15, false, true);
		
		degrees = ThreadLocalRandom.current().nextInt(0, 360);
		
		generated_color = new BufferedImage(can_color.getWidth(null), can_color.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = generated_color.createGraphics();
	    bGr.drawImage(can_color, 0, 0, null);
	    bGr.dispose();
	    
	    Color randomColor = COLORS[ThreadLocalRandom.current().nextInt(COLORS.length)];
	    setColor(randomColor, 100);
	}

	private void setColor(Color color, int l) {
		for(int col = 0; col < generated_color.getWidth(); col++) {
			for(int row = 0; row < generated_color.getHeight(); row++) {
				if((generated_color.getRGB(col, row)>>24) != 0x00) {
					generated_color.setRGB(col, row, new Color(color.getRed() * l/255, color.getGreen() * l/255, color.getBlue() * l/255).getRGB());
				}
			}
		}
	}
	
	@Override
	public void onInteract(MapPlayer player) {
		
	}

	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		AffineTransform affineTransform = g2d.getTransform();
		g2d.rotate(Math.toRadians(degrees), getX(), getY());
		g2d.drawImage(generated_color, (int) (getX() - getW() / 2), (int) (getY() - getH() / 2), null);
		g2d.drawImage(can_top, (int) (getX() - getW() / 2), (int) (getY() - getH() / 2), null);
		g2d.setTransform(affineTransform);
	}

}
