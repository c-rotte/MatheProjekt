package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import java.awt.Graphics2D;
import java.awt.Image;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.ResLoader;

public class RaceBackgroundHelper extends ScreenElement{

	private static Image raceBG = ResLoader.getImageByName("game/minigames/race/race.png");
	
	public RaceBackgroundHelper(Scene container, int x, int y) {
		super(container, x, y, 1280, 50);
	}

	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(raceBG, (int) getX(), (int) getY(), (int) getW(), (int) getH(), null);
	}
	
}
