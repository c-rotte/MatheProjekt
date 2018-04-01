package projekt.mathe.game.mathespiel.scenes.game.minigames.area.models;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Parallelogramm extends Model{

	public Parallelogramm(Point A, Point B, Point C, Point D) {
		super(TYPE.PARALLELOGRAMM, A, B, C, D);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setStroke(new BasicStroke(4f));
		g2d.setColor(Color.GREEN);
		g2d.draw(getPolygon());
	}

}
