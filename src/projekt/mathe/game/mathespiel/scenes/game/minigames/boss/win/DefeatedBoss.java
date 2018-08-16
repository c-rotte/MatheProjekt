package projekt.mathe.game.mathespiel.scenes.game.minigames.boss.win;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.ResLoader;

public class DefeatedBoss extends ScreenElement{

	private static final Image boss = ResLoader.getImageByName("game/minigames/boss/win/boss_def.png");
	
	private Animator moveAnimator;
	private float const_amount;
	private float rotation;
	
	public DefeatedBoss(Scene container) {
		super(container, 170, 270, 77, 100);
		moveAnimator = new Animator(26, 1);
		const_amount = 0.5f;
		rotation = 0;
	}

	@Override
	public void onTick(float delta) {
		moveAnimator.calculate(delta);
		addToX((const_amount + moveAnimator.getCurrValue()) * delta);
		if(moveAnimator.finished()) {
			addToY(0.1f * delta);
		}
		rotation += (const_amount + moveAnimator.getCurrValue()) * delta;
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		AffineTransform transform = g2d.getTransform();
		g2d.rotate(Math.toRadians(rotation), getBounds().getCenterX(), getBounds().getCenterY());
		g2d.drawImage(boss, (int) getX(), (int) getY(), null);
		g2d.setTransform(transform);
	}

}
