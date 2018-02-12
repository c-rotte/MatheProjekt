package projekt.mathe.game.mathespiel.scenes.game.pause;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.pause.PauseScreen;

public class MainPauseScreen extends PauseScreen{

	private float aimX;
	private static final float SPEED = 20;
	private Animator fillAnimator;
	
	public MainPauseScreen(Scene container) {
		super(container, 200, 100, 880, 520, KeyEvent.VK_ESCAPE);
		aimX = x;
		x = -w;
		fillAnimator = new Animator(aimX + w, SPEED);
		fillAnimator.setValue(0);
	}

	@Override
	public void onTick(float delta) {
		if(isOpen()) {
			if(x < aimX) {
				x += SPEED * delta;
				fillAnimator.calculate(delta);
			}else {
				x = aimX;
			}
		}else {
			if(x > -w - 1) {
				x -= SPEED * delta;
				fillAnimator.calculate(delta);
			}else {
				x = -w - 1;
			}
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		if(!isOpen()) {
			getContainer().fillScene(g2d, Color.BLACK, (1 - fillAnimator.getCurrValueRelative()) / 2);
		}else {
			getContainer().fillScene(g2d, Color.BLACK, fillAnimator.getCurrValueRelative() / 2);
		}
		Composite composite = g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
		g2d.setColor(Color.GRAY);
		g2d.fill(getContainer().camera.translateAbsolutBounds(getBounds()));
		g2d.setComposite(composite);
		Helper.drawStringAroundPoint(getContainer().camera.translateAbsolutX(getMiddle().x), getContainer().camera.translateAbsolutY(getMiddle().y), "Pause", Color.WHITE, 40, FONT.Chrobot, g2d);
	}

	@Override
	public void onToggle() {
		fillAnimator.reset();
	}

}
