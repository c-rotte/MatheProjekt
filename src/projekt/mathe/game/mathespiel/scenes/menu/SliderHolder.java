package projekt.mathe.game.mathespiel.scenes.menu;

import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;

public class SliderHolder extends Holder<Slider>{

	public SliderHolder(Scene container) {
		super(container);
	}

	public void reset() {
		for(Slider slider : getElements()) {
			slider.reset();
		}
	}
	
	public boolean wasClicked() {
		for(Slider slider : getElements()) {
			if(slider.getState().equals("clicked")) {
				return true;
			}
		}
		return false;
	}
	
}
