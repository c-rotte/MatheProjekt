package projekt.mathe.game.engine.help;

import java.awt.Rectangle;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.Values;

public class Camera {

	private float x, y;
	private Scene container;
	private Rectangle maxBounds;
	
	public Camera(int x, int y, Scene container) {
		this.x = x;
		this.y = y;
		this.container = container;
	}

	public void setMaxBounds(Rectangle maxBounds) {
		this.maxBounds = maxBounds;
	}
	
	public boolean outOfMaxBoundsX() {
		if(maxBounds == null) {
			throw new NullPointerException("MaxBounds not set");
		}
		if(getFocusX() - Values.WINDOW_WIDTH/2 < maxBounds.x || getFocusX() + Values.WINDOW_WIDTH/2 > maxBounds.x + maxBounds.width) {
			return true;
		}
		return false;
	}
	
	public boolean outOfMaxBoundsY() {
		if(maxBounds == null) {
			throw new NullPointerException("MaxBounds not set");
		}
		if(getFocusY() - Values.WINDOW_HEIGHT/2 < maxBounds.y || getFocusY() + Values.WINDOW_HEIGHT/2 > maxBounds.y + maxBounds.height) {
			return true;
		}
		return false;
	}
	
	public void moveX(float amount) {
		x += amount;
	}
	
	public void moveY(float amount) {
		y += amount;
	}

	public boolean inRangeOfCamera(float x, float y, float w, float h) {
		boolean xb = x > this.x - w && x < this.x + Values.WINDOW_WIDTH;
		boolean yb = y > this.y - h && y < this.y + Values.WINDOW_HEIGHT;
		return xb && yb;
	}

	public void focusX(float x) {
		setX((float) (x - (Values.WINDOW_WIDTH/2)));
	}
	
	public void focusY(float y) {
		setY((float) (y - (Values.WINDOW_HEIGHT/2)));
	}
	
	public float getFocusX() {
		return (float) ((getX() + ((Values.WINDOW_WIDTH)/2)));
	}
	
	public float getFocusY() {
		return (float) ((getY() + ((Values.WINDOW_HEIGHT)/2)));
	}
	
	public float getX() {
		return Math.round(x);
	}

	public float getRawX() {
		return x;
	}
	
	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return Math.round(y);
	}

	public float getRawY() {
		return y;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public int translateAbsolutX(float x) {
		x *= container.container.xRatio;
		x -= container.container.xOffset;
		x /= container.container.xRatio;
		return (int) Math.round(x + getX() + container.container.xOffset);
	}
	
	public int translateAbsolutY(float y) {
		y *= container.container.yRatio;
		y -= container.container.yOffset;
		y /= container.container.yRatio;
		return (int) Math.round(y + getY());
	}
	
	public Rectangle translateAbsolutBounds(Rectangle bounds) {
		return new Rectangle((int) translateAbsolutX((float) bounds.getX()), (int) translateAbsolutY((float) bounds.getY()), (int) bounds.getWidth(), (int) bounds.getHeight());
	}

}
