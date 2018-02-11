package projekt.mathe.game.mathespiel.scenes.game.world.barrier;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;
import projekt.mathe.game.mathespiel.Settings;

public class BarrierHolder extends Holder<Barrier>{

	public BarrierHolder(Scene container) {
		super(container);
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
		if(Settings.HITBOXEN_ANZEIGEN) {
			super.onPaint(g2d);
		}
	}
	
}
