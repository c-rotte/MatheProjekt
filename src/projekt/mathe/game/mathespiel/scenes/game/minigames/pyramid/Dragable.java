package projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid;

import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;

public abstract class Dragable extends ScreenElement{

	private boolean grabbed;
	private float xDiff, yDiff;
	
	public Dragable(Scene container, int x, int y, int w, int h) {
		super(container, x, y, w, h);
	}
	
	public boolean wasGrabbed() {
		return grabbed;
	}

	@Override
	public void onMousePressed(MouseEvent e) {
		if(getBounds().contains(e.getPoint())) {
			grabbed = true;
			xDiff = e.getX() - x;
			yDiff = e.getY() - y;
		}
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		grabbed = false;
	}
	
	@Override
	public void onMouseExited(MouseEvent e) {
		grabbed = false;
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		if(grabbed) {
			x = e.getX() - xDiff;
			y = e.getY() - yDiff;
		}
	}
	
}
