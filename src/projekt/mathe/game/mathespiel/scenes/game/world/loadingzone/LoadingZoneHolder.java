package projekt.mathe.game.mathespiel.scenes.game.world.loadingzone;

import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;
import projekt.mathe.game.mathespiel.Settings;

public class LoadingZoneHolder extends Holder<LoadingZone>{

	public LoadingZoneHolder(Scene container) {
		super(container);
	}
	
	@Override
	public void onPaint(Graphics2D g2d) {
		if(Settings.HITBOXEN_ANZEIGEN) {
			super.onPaint(g2d);
		}
	}
	
}
