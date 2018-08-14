package projekt.mathe.game.mathespiel.scenes.menu;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;

public abstract class Warning extends ScreenElement{

	private boolean open;
	
	public Warning(Scene container, int x, int y, int w, int h) {
		super(container, x, y, w, h);
	}

	public void open() {
		open = true;
	}
	
	public void close() {
		open = false;
	}

	public boolean isOpen() {
		return open;
	}
	
}
