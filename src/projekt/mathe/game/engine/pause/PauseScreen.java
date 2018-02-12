package projekt.mathe.game.engine.pause;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;

public abstract class PauseScreen extends ScreenElement{
	
	private boolean open;
	private int key;
	private boolean canToggle;

	public PauseScreen(Scene container, int x, int y, int w, int h, int key) {
		super(container, x, y, w, h);
		this.key = key;
		canToggle = true;
	}

	public boolean toggle() {
		if(!open && getContainer().fading) {
			return false;
		}
		getContainer().keyController.reset();
		open = !open;
		onToggle();
		return true;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public abstract void onToggle();
	
	public void checkKeyPress() {
		if(canToggle && getContainer().keyController.isPressed(key)) {
			if(toggle()) {
				canToggle = false;
			}
		}else if(!getContainer().keyController.isPressed(key)){
			canToggle = true;
		}
	}
	
}
