package projekt.mathe.game.mathespiel.scenes.game.minigames.blackboard;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;

public class TimeBar extends ScreenElement{

	private Animator animator;
	private float offset;
	private boolean finished;
	
	public TimeBar(Scene container, int x, int y, int w, int h, int frames, float offset) {
		super(container, x, y, w, h);
		animator = new Animator(frames, 1);
		this.offset = offset;
	}

	public void reset() {
		animator.reset();
		finished = false;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	@Override
	public void onTick(float delta) {
		animator.calculate(delta);
		if(animator.finished()) {
			finished = true;
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(Color.GRAY);
		g2d.fill(getBounds());
		g2d.setColor(Color.WHITE);
		g2d.fillRect((int) (getX() + offset), (int) (getY() + offset), (int) (getW() - 2 * offset), (int) (getH() - 2 * offset));
		g2d.setColor(Color.RED);
		g2d.fillRect((int) (getX() + offset), (int) (getY() + offset), (int) ((getW() - 2 * offset) * (1f - animator.getCurrValueRelative())), (int) (getH() - 2 * offset));
	}

}
