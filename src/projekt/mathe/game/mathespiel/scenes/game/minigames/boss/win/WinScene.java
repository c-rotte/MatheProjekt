package projekt.mathe.game.mathespiel.scenes.game.minigames.boss.win;

import java.awt.Graphics2D;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class WinScene extends Scene{

	private MainSceneData mainSceneData;
	
	private DefeatedBoss defeatedBoss;
	
	public WinScene(Game container) {
		super(container, "boss_win", Values.SCENE_BG_COLOR);
		registerWorld(World.emptyInstance(this));
		defeatedBoss = new DefeatedBoss(this);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		mainSceneData = (MainSceneData) sceneData;
	}

	@Override
	public void onTick(float delta) {
		defeatedBoss.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		defeatedBoss.onPaint(g2d);
	}

	@Override
	public SceneData getDataForNextScene() {
		return mainSceneData;
	}

}
