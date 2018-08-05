package projekt.mathe.game.engine.minigame;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashMap;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.save.Saver;

public abstract class MiniGame {

	private String id;
	private HashMap<String, Object> stats;
	public Scene container;
	private boolean mouseBlocked;
	
	public MiniGame(Scene container, String id) {
		this.id = id;
		this.container = container;
		stats = Saver.getMinigameStats(id);
	}
	
	public void setMouseBlocked(boolean mouseBlocked) {
		this.mouseBlocked = mouseBlocked;
	}
	
	public boolean isMouseBlocked() {
		return mouseBlocked;
	}
	
	public void setStat(String id, Object object) {
		stats.put(id, object);
	}
	
	public void saveStats() {
		for(String string : stats.keySet()) {
			Saver.setData(id + "_" + string, stats.get(string));
		}
	}
	
	public abstract void onTick(float delta);
	
	public abstract void onPaint(Graphics2D g2d);
	
	public void onMousePressed(MouseEvent e) {}
	public void onMouseReleased(MouseEvent e) {}
	public void onMouseClicked(MouseEvent e) {}
	public void onMouseDragged(MouseEvent e) {}
	public void onMouseMoved(MouseEvent e) {}
	public void onMouseWheelMoved(MouseWheelEvent e) {}
	public void onMouseExited(MouseEvent e) {}
	
}
