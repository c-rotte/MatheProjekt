package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;

public class Countdown extends ScreenElement{

	private int absolutX, absolutY;
	private Animator animator;
	private int currNumber;
	
	public Countdown(Scene container) {
		super(container, container.camera.translateAbsolutX(-1), container.camera.translateAbsolutY(-1), 150, -1);
		this.absolutX = 640;
		this.absolutY = 500;
		animator = new Animator(60, 1);
		currNumber = 5;
	}

	public boolean started() {
		return currNumber <= 0;
	}
	
	@Override
	public void onTick(float delta) {
		setX(getContainer().camera.translateAbsolutX(absolutX));
		setY(getContainer().camera.translateAbsolutY(absolutY));
		if(currNumber > -1 && !getContainer().fading) {
			animator.calculate(delta);
			if(animator.finished()) {
				currNumber--;
				animator.reset();
			}
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		if(currNumber > -1 && !getContainer().fading) {
			if(currNumber >= 1) {
				float amount = 4;
				float degree = 15;
				g2d.rotate(Math.toRadians(degree - (animator.getCurrValueRelative() * degree * amount > degree ? degree : animator.getCurrValueRelative() * degree * amount)), getX(), getY());
			}
			Helper.drawStringAroundPosition((int) getX(), (int) getY(), started() ? "START" : currNumber + "", Color.WHITE, (int) getW(), FONT.VCR, g2d, Color.BLACK, 13f);
		}
	}
	
}
