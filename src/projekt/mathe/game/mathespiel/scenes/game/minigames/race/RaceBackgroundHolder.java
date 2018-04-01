package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;
import projekt.mathe.game.engine.help.Camera;

public class RaceBackgroundHolder extends Holder<RaceBackgroundHelper>{

	private float lastCameraY;
	private float lastY;
	
	public RaceBackgroundHolder(Scene container) {
		super(container);
		lastCameraY = container.camera.getY() + 720;
		for(int i = 0; i < 15; i++) {
			addElement(new RaceBackgroundHelper(container, 0, 750 - i * 50));
		}
	}

	@Override
	public void addElement(RaceBackgroundHelper t) {
		lastY = t.getY();
		super.addElement(t);
	}
	
	@Override
	public void onTick(float delta) {
		if(lastCameraY - container.camera.getY() + 50 >= 50) {
			lastCameraY = container.camera.getY();
			addElement(new RaceBackgroundHelper(container, 0, (int) (lastY - 50)));
		}
		super.onTick(delta);
	}
	
}
