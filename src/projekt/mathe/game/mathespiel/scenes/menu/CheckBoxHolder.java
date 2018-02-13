package projekt.mathe.game.mathespiel.scenes.menu;

import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;

public class CheckBoxHolder extends Holder<CheckBox>{

	private boolean clickable;
	
	public CheckBoxHolder(Scene container) {
		super(container);
	}

	public boolean wasClicked(String id) {
		for(CheckBox checkBox : getElements()) {
			if(checkBox.getID().equals(id)) {
				return checkBox.wasClicked();
			}
		}
		throw new NullPointerException("Element wasn't found!");
	}
	
	public void setClickable(boolean clickable) {
		this.clickable = clickable;
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		if(clickable) {
			super.onMouseClicked(e);
		}
	}
	
}
