package projekt.mathe.game.mathespiel.scenes.game.minigames.boss.win;

import java.awt.Graphics2D;
import java.awt.Image;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.save.Saver;

public class WinAnimation extends ScreenElement{

	private static final Image bg = ResLoader.getImageByName("game/minigames/boss/win/bg.jpg");
	
	private DefeatedBoss defeatedBoss;
	
	private Fragments fragments;
	
	private Animator callAnimator;
	private boolean called;
	
	public WinAnimation(Scene container) {
		super(container, 0, 0, 0, 0);
		defeatedBoss = new DefeatedBoss(container);
		fragments = new Fragments(container, 300, 320);
		callAnimator = new Animator(50f, 1);
	}

	@Override
	public void onTick(float delta) {
		defeatedBoss.onTick(delta);
		fragments.onTick(delta);
		callAnimator.calculate(delta);
		if(callAnimator.finished() && !called) {
			called = true;
			Saver.setData("bossdefeated", true);
			getContainer().callScene("pausenhof", getContainer().getDataForNextScene(), 100f);
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(bg, 0, 0, null);
		defeatedBoss.onPaint(g2d);
		fragments.onPaint(g2d);
	}

}
