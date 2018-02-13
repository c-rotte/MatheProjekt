package projekt.mathe.game.engine.pause;

import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;

public class PauseScreenClickableHolder extends Holder<PauseScreenClickable>{

	public PauseScreenClickableHolder(Scene container) {
		super(container);
	}

	@Override
	public void onTick(float delta) {
		for(PauseScreenClickable clickable : getElements()) {
			clickable.onClickableTick(delta);
		}
	}
	
	public void onMouseMoved(MouseEvent e) {
		for(PauseScreenClickable clickable : getElements()) {
			clickable.onMouseDragged(e);
		}
	}

	public void onMouseDragged(MouseEvent e) {
		for(PauseScreenClickable clickable : getElements()) {
			clickable.onMouseDragged(e);
		}
	}
		
	public void onMouseClicked(MouseEvent e) {
		for(PauseScreenClickable clickable : getElements()) {
			clickable.onMouseClicked(e);
		}
	}
	
	public void reset() {
		for(PauseScreenClickable clickable : getElements()) {
			clickable.reset();
		}
	}
	
}
