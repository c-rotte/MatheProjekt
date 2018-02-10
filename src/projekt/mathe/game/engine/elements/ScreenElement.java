package projekt.mathe.game.engine.elements;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import projekt.mathe.game.engine.Scene;

public abstract class ScreenElement {

	private Scene container;
	public float x, y, w, h;
	
	public ScreenElement(Scene container, int x, int y, int w, int h) {
		this.container = container;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public Scene getContainer() {
		return container;
	}

	public void setContainer(Scene container) {
		this.container = container;
	}

	public abstract void onTick(float delta);
	
	//Fürs aufrufen aus der Scene
	public final void onPerformacePaint(Graphics2D g2d) {
		if(container.camera.inRangeOfCamera(x, y, w, h)) {
			onPaint(g2d);
		}
	}
	
	//Für den User zum überschreiben
	public abstract void onPaint(Graphics2D g2d);
	
	public boolean onScreen() {
		return container.camera.inRangeOfCamera(x, y, w, h);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int) x, (int) y, (int) w, (int) h);
	}
	
	public Point getMiddle() {
		Point point = new Point((int) (x + w/2), (int) (y + h/2));
		return point;
	}
	
}
