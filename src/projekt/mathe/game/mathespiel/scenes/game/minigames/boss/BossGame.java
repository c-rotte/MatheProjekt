package projekt.mathe.game.mathespiel.scenes.game.minigames.boss;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.minigame.MiniGame;

public class BossGame extends MiniGame{

	private Wand wand;
	private Slingshot slingshot;
	
	public BossGame(Scene container) {
		super(container, "boss");
		wand = new Wand(container);
		slingshot = new Slingshot(container);
		setMouseBlocked(true);
	}
	
	public Slingshot getSlingshot() {
		return slingshot;
	}
	
	public Wand getWand() {
		return wand;
	}
	
	@Override
	public void onTick(float delta) {
		slingshot.onTick(delta);
		wand.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		wand.onPaint(g2d);
		slingshot.onPaint(g2d);
		wand.drawBubble(g2d);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		if(!isMouseBlocked()) {
			slingshot.onMouseMoved(e);
		}
		super.onMouseMoved(e);
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		if(!isMouseBlocked()) {
			slingshot.onMouseDragged(e);
		}
		super.onMouseDragged(e);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		if(!isMouseBlocked()) {
			slingshot.onMousePressed(e);
		}
		super.onMousePressed(e);
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		if(!isMouseBlocked()) {
			slingshot.onMouseReleased(e);
		}
		super.onMouseReleased(e);
	}
	
	@Override
	public void onMouseExited(MouseEvent e) {
		if(!isMouseBlocked()) {
			slingshot.onMouseExited(e);
		}
		super.onMouseExited(e);
	}
	
}
