package projekt.mathe.game.engine.minigame;

import java.awt.Graphics2D;
import java.util.HashMap;

import projekt.mathe.game.engine.save.Saver;

public abstract class MiniGame {

	private String id;
	private HashMap<String, Object> stats;
	
	public MiniGame(String id) {
		this.id = id;
		stats = Saver.getMinigameStats(id);
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
	
}
