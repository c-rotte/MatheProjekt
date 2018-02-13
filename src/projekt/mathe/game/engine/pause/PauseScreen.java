package projekt.mathe.game.engine.pause;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;

public abstract class PauseScreen extends ScreenElement{

	private float SPEED = 10;
	private float selectedX, selectedY;
	private float aimX, startX;
	private boolean clickable;
	private String state; //"hidden", "fadingIn", "shown", "fadingOut"
	private int keycode;
	private PauseScreenClickableHolder holder;
	public float xUnterschied;
	
	public PauseScreen(Scene container, int x, int y, int w, int h) {
		super(container, x, y, w, h);
		state = "hidden";
		clickable = true;
		selectedX = x;
		selectedY = y;
		xUnterschied = x + w;
		keycode = KeyEvent.VK_ESCAPE;
		holder = new PauseScreenClickableHolder(container);
	}

	public float getSPEED() {
		return SPEED;
	}
	
	public void reset() {
		holder.reset();
		state = "hidden";
	}
	
	public PauseScreen setSpeed(float SPEED) {
		this.SPEED = SPEED;
		return this;
	}
	
	public PauseScreen setKeyCode(int keycode) {
		this.keycode = keycode;
		return this;
	}
	
	public PauseScreenClickableHolder getHolder() {
		return holder;
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		if(!state.equals("hidden")) {
			holder.onMouseMoved(e);
		}
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		if(!state.equals("hidden")) {
			holder.onMouseDragged(e);
		}
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		if(!state.equals("hidden")) {
			holder.onMouseClicked(e);
		}
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		if(!state.equals("hidden")) {
			holder.onMousePressed(e);
		}
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		if(!state.equals("hidden")) {
			holder.onMouseReleased(e);
		}
	}
	
	@Override
	public void onMouseExited(MouseEvent e) {
		if(!state.equals("hidden")) {
			holder.onMouseExited(e);
		}
	}
	
	@Override
	public void onMouseWheelMoved(MouseWheelEvent e) {
		if(!state.equals("hidden")) {
			holder.onMouseWheelMoved(e);
		}
	}
	
	public void onToggle() {
		
	}
	
	public final void onScreenTick(float delta) {
		boolean pressed = getContainer().keyController.isPressed(keycode) && !getContainer().fading;
		boolean interacted = pressed && clickable;
		if(pressed) {
			clickable = false;
		}else {
			clickable = true;
		}
		switch (state) {
			case "hidden": 
				if(interacted) {
					startX = getContainer().camera.translateAbsolutX(0) - w;
					x = startX;
					aimX = getContainer().camera.translateAbsolutX(selectedX);
					y = getContainer().camera.translateAbsolutY(selectedY);
					state = "fadingIn";
					onToggle();
				}
				break;
			case "fadingIn":
				if(x < aimX) {
					x += SPEED * delta;
				}else {
					x = aimX;
					state = "shown";
				}
				break;
			case "shown": 
				if(interacted) {
					state = "fadingOut";
					onToggle();
				}
				break;
			case "fadingOut":
				y = getContainer().camera.translateAbsolutY(selectedY);
				startX = getContainer().camera.translateAbsolutX(0) - w;
				if(x > startX) {
					x -= SPEED * delta;
				}else {
					x = startX;
					state = "hidden";
				}
				break;
		}
		onTick(delta);
	}
	
	public boolean isOpen() {
		return state.equals("shown") || state.equals("fadingIn");
	}
	
	public String getState() {
		return state;
	}

}
