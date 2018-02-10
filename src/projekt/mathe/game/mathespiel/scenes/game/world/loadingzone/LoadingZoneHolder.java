package projekt.mathe.game.mathespiel.scenes.game.world.loadingzone;

import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;

public class LoadingZoneHolder extends Holder<LoadingZone>{

	private boolean visible;
	
	public LoadingZoneHolder(Scene container, boolean visible) {
		super(container);
		this.visible = visible;
	}
	
	@Override
	public void onPaint(Graphics2D g2d) {
		if(visible) {
			super.onPaint(g2d);
		}
	}
	
}
