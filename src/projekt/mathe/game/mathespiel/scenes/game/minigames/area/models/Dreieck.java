package projekt.mathe.game.mathespiel.scenes.game.minigames.area.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Dreieck extends Model{

	public Dreieck(Point A, Point B, Point C) {
		super(TYPE.DREIECK, A, B, C, null);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(4f));
		g2d.setColor(Color.YELLOW);
		g2d.draw(getPolygon());
	}

}
