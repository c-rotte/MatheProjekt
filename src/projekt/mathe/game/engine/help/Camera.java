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
	
	public boolean outOfMaxBounds() {
		if(maxBounds == null) {
			throw new NullPointerException("MaxBounds not set");
		}
		if(getFocusX() - Values.WINDOW_WIDTH/2 < maxBounds.x || getFocusX() + Values.WINDOW_WIDTH/2 > maxBounds.x + maxBounds.width) {
			return true;
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

	public boolean inRangeOfCamera(float x2, float y2, float w, float h) {
		return this.x <= x2 + w && this.x + Values.WINDOW_WIDTH * container.container.xRatio > x2 && this.y <= y2 + h && this.y + (Values.WINDOW_HEIGHT + Values.TITLEBAR_HEIGHT) * container.container.yRatio > y2 - h;
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
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	public int translateAbsolutX(float x) {
		x *= container.container.xRatio;
		x -= container.container.xOffset;
		x /= container.container.xRatio;
		return (int) (x + getX() + container.container.xOffset);
	}
	
	public int translateAbsolutY(float y) {
		y *= container.container.yRatio;
		y -= container.container.yOffset;
		y /= container.container.yRatio;
		if(container.container.fullscreen) {
			return (int) (y + getY());
		}else {
			return (int) (y + getY());
		}
	}
	
}
