package projekt.mathe.game.mathespiel.scenes.menu;

import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;

public class CheckBoxHolder extends Holder<CheckBox>{

	public CheckBoxHolder(Scene container) {
		super(container);
	}

	public void onMouseDragged(MouseEvent e) {
		for(CheckBox checkBox : getElements()) {
			checkBox.onMouseDragged(e);
		}
	}
	
	public void onMouseClicked(MouseEvent e) {
		for(CheckBox checkBox : getElements()) {
			checkBox.onMouseClicked(e);
		}
	}
	
	public boolean wasClicked(String id) {
		for(CheckBox checkBox : getElements()) {
			if(checkBox.getID().equals(id)) {
				return checkBox.wasClicked();
			}
		}
		throw new NullPointerException("Element wasn't found!");
	}
	
}
