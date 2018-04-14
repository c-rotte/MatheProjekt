package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.mathespiel.scenes.game.minigames.race.Questionfield.ComplexChest;

public class QuestionPainter extends ScreenElement{

	private int absolutX, absolutY;
	private String text;
	private ComplexChest complexChest;
	
	public QuestionPainter(Scene container, Questionfield questionfield, String q) {
		super(container, -1, -1, 800, 300);
		absolutX = 240;
		absolutY = 370;
		if(!q.contains(" : ")) {
			text = q;
		}else {
			complexChest = questionfield.generateComplexChest(getAbsolutX() + 400, getAbsolutY() + 50, q);
		}
	}

	public int getAbsolutX() {
		return absolutX;
	}
	
	public int getAbsolutY() {
		return absolutY;
	}
	
	@Override
	public void onTick(float delta) {
		setX(getContainer().camera.translateAbsolutX(absolutX));
		setY(getContainer().camera.translateAbsolutY(absolutY));
		if(text == null) {
			complexChest.onTick(delta);
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		if(text == null) {
			complexChest.onPaint(g2d);
		}else {
			Helper.drawStringAroundPosition((int) getX() + 400, (int) getY() + 50, text, Color.WHITE, 26, FONT.VCR, g2d, null, -1);
		}
	}

}
