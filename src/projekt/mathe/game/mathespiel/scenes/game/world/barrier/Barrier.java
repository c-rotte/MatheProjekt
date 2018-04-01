package projekt.mathe.game.mathespiel.scenes.game.world.barrier;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.InputStream;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class Barrier extends ScreenElement{

	public Barrier(Scene container, int x, int y, int w, int h) {
		super(container, x, y, w, h);
	}
	
	public Barrier(Scene container, Point leftTop, Point rightBottom) {
		super(container, leftTop.x, leftTop.y, rightBottom.x - leftTop.x, rightBottom.y - leftTop.y);
	}
	
	public boolean doesCollide(Rectangle rectangle) {
		return getBounds().intersects(rectangle);
	}
	
	public static void addBarriersFromFile(InputStream inputStream, World world) {
		Point[] points = Helper.getCoordsByFile(inputStream);
		for(int i = 0; i < points.length; i+=2) {
			world.addBarrier(new Barrier(world.container, points[i], points[i + 1]));
		}
	}

	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(Color.RED);
		g2d.drawRect((int) getX(), (int) getY(), (int) getW(), (int) getH());
	}
	
}
