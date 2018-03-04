package projekt.mathe.game.mathespiel.scenes.menu;

import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;

public class ButtonHolder extends Holder<Button>{

	public ButtonHolder(Scene container) {
		super(container);
	}

	public void reset() {
		for(Button slider : getElements()) {
			slider.reset();
		}
	}

	public boolean wasClicked() {
		for(Button slider : getElements()) {
			if(slider.getState().equals("clicked")) {
				return true;
			}
		}
		return false;
	}
	
}
