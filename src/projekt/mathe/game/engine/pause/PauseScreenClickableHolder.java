package projekt.mathe.game.engine.pause;

import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;

public class PauseScreenClickableHolder extends Holder<PauseScreenClickable>{

	private boolean oneTimeClicked;
	
	public PauseScreenClickableHolder(Scene container) {
		super(container);
	}

	@Override
	public void onTick(float delta) {
		for(PauseScreenClickable clickable : getElements()) {
			clickable.onClickableTick(delta);
		}
	}

	public void reset() {
		for(PauseScreenClickable clickable : getElements()) {
			clickable.reset();
		}
		oneTimeClicked = false;
	}

	public boolean isOneTimeClicked() {
		return oneTimeClicked;
	}

	public void setOneTimeClicked(boolean oneTimeClicked) {
		this.oneTimeClicked = oneTimeClicked;
	}
	
}
