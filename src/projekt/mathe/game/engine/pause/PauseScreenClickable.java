package projekt.mathe.game.engine.pause;

import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;

public abstract class PauseScreenClickable extends ScreenElement{

	private PauseScreen pauseScreen;
	private Runnable runnable;
	private float xAbstand, yAbstand;
	
	private String state; //"normal", "selected", "clicked"
	
	public PauseScreenClickable(Scene container, PauseScreen pauseScreen, int x, int y, int w, int h) {
		super(container, x, y, w, h);
		this.pauseScreen = pauseScreen;
		xAbstand = x;
		yAbstand = y;
		state = "normal";
	}

	public void addRunnable(Runnable runnable) {
		this.runnable = runnable;
	}
	
	public PauseScreen getPauseScreen() {
		return pauseScreen;
	}
	
	public void reset() {
		state = "normal";
	}
	
	public void onMouseDragged(MouseEvent e) {
		if(!state.equals("clicked")) {
			if(getBounds().contains(e.getPoint())) {
				state = "selected";
			}else {
				state = "normal";
			}
		}
	}
	
	public void onMouseClicked(MouseEvent e) {
		if(!state.equals("clicked") && getBounds().contains(e.getPoint())) {
			state = "clicked";
			if(runnable != null) {
				runnable.run();
			}
		}
	}
	
	public String getState() {
		return state;
	}
	
	public final void onClickableTick(float delta) {
		x = pauseScreen.x + xAbstand;
		y = pauseScreen.y + yAbstand;
		onTick(delta);
	}
	
}
