package projekt.mathe.game.mathespiel.scenes.game.minigames.boss.win;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;

public class WinScene extends Scene{

	private MainSceneData mainSceneData;
	private WinAnimation winAnimation;
	
	public WinScene(Game container) {
		super(container, "boss_win", Values.SCENE_BG_COLOR);
		winAnimation = new WinAnimation(this);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		mainSceneData = (MainSceneData) sceneData;
	}

	@Override
	public void onTick(float delta) {
		winAnimation.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		winAnimation.onPaint(g2d);
	}

	@Override
	public SceneData getDataForNextScene() {
		return mainSceneData;
	}

}
