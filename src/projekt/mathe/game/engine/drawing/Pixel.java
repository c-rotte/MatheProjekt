package projekt.mathe.game.engine.drawing;

import java.awt.Color;
import java.awt.Graphics2D;

public class Pixel {

	private int x, y;
	private Color color;
	private int radius;
	
	public Pixel(int x, int y, Color color, int radius) {
		this.x = x;
		this.y = y;
		this.color = color;
		this.radius = radius;
	}

	public void onPaint(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fillRect(x - radius/2, y - radius/2, radius, radius);
	}
	
}
