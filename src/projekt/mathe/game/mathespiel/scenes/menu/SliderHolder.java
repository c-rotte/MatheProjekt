package projekt.mathe.game.mathespiel.scenes.menu;

import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;

public class SliderHolder extends Holder<Slider>{

	public SliderHolder(Scene container) {
		super(container);
	}
	
	public void onMouseDragged(MouseEvent e) {
		for(Slider slider : getElements()) {
			slider.onMouseDragged(e);
		}
	}
	
	public void onMouseClicked(MouseEvent e) {
		if(!wasClicked()) {
			for(Slider slider : getElements()) {
				slider.onMouseClicked(e);
			}
		}
	}
	
	public boolean wasClicked() {
		for(Slider slider : getElements()) {
			if(!slider.isInteractable()) {
				return true;
			}
		}
		return false;
	}
	
	public void reset() {
		for(Slider slider : getElements()) {
			slider.setInteractable(true);
		}
	}
	
}
