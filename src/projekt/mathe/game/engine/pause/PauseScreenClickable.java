package projekt.mathe.game.engine.pause;

import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;

public abstract class PauseScreenClickable extends ScreenElement{

	private PauseScreen pauseScreen;
	private Runnable runnable;
	private float xAbstand, yAbstand;
	private int t;
	private int timesClicked;
	private String state; //"normal", "selected", "clicked"
	private boolean pressable;
	
	public PauseScreenClickable(Scene container, PauseScreen pauseScreen, int x, int y, int w, int h) {
		super(container, x, y, w, h);
		this.pauseScreen = pauseScreen;
		xAbstand = x;
		yAbstand = y;
		state = "normal";
		t = 1;
		timesClicked = 0;
		pressable = true;
	}

	public PauseScreenClickable setMaxClickTimes(int t) {
		this.t = t;
		return this;
	}
	
	public void addRunnable(Runnable runnable) {
		this.runnable = runnable;
	}
	
	public PauseScreen getPauseScreen() {
		return pauseScreen;
	}
	
	public void reset() {
		state = "normal";
		timesClicked = 0;
		pressable = true;
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		if(!(state.equals("clicked") && timesClicked >= t && t > 0)) {
			if(getBounds().contains(e.getPoint())) {
				state = "selected";
			}else {
				state = "normal";
			}
		}
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		if(!(state.equals("clicked") && timesClicked >= t && t > 0)) {
			if(getBounds().contains(e.getPoint())) {
				state = "selected";
			}else {
				state = "normal";
			}
		}
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		if(getBounds().contains(e.getPoint())) {
			if(pressable) {
				pressable = false;
				if((timesClicked < t || t <= 0) && !getPauseScreen().getHolder().isOneTimeClicked()) {
					state = "clicked";
					timesClicked++;
					if(timesClicked == t) {
						getPauseScreen().getHolder().setOneTimeClicked(true);
					}
					if(runnable != null) {
						runnable.run();
					}
				}
			}
		}
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		if(getBounds().contains(e.getPoint())) {
			state = "selected";
			pressable = true;
		}
	}
	
	public String getState() {
		return state;
	}
	
	public final void onClickableTick(float delta) {
		setX(pauseScreen.getX() + xAbstand);
		setY(pauseScreen.getY() + yAbstand);
		onTick(delta);
	}
	
}
