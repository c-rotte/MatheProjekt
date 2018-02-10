package projekt.mathe.game.mathespiel.scenes.game.world.barrier;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;

public class BarrierHolder extends Holder<Barrier>{

	private boolean visible;
	
	public BarrierHolder(Scene container, boolean visible) {
		super(container);
		this.visible = visible;
	}
	
	public boolean doesCollide(Rectangle rectangle) {
		for(Barrier barrier : getElements()) {
			if(barrier.doesCollide(rectangle)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void onPaint(Graphics2D g2d) {
		if(visible) {
			super.onPaint(g2d);
		}
	}
	
}
