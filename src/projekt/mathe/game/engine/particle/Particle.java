package projekt.mathe.game.engine.particle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;

public class Particle extends ScreenElement{
	
	private float SPEED;
	private float xDist, yDist;
	private Color COLOR;
	
	public Particle(Scene container, Point currPos, Point aim, float SPEED, int RADIUS, Color COLOR) {
		super(container, currPos.x, currPos.y, RADIUS * 2, RADIUS * 2);
		this.SPEED = SPEED;
		this.COLOR = COLOR;
		xDist = aim.x - currPos.x;
		yDist = aim.y - currPos.y;
		float length = (float) Math.sqrt(Math.pow(xDist, 2) + Math.pow(yDist, 2));
		xDist /= length;
		yDist /= length;
	}

	public void setCOLOR(Color cOLOR) {
		COLOR = cOLOR;
	}
	
	public boolean outOfBounds() {
		return !getContainer().camera.inRangeOfCamera(getX(), getY(), getW(), getH());
	}
	
	@Override
	public void onTick(float delta) {
		addToX(xDist * SPEED * delta);
		addToY(yDist * SPEED * delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(COLOR);
		g2d.fillOval((int) (getX() - getW()/2), (int) (getY() - getH()/2), (int) getW(), (int) getH());
	}

}
