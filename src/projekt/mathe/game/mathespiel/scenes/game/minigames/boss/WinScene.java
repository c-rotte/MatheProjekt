package projekt.mathe.game.mathespiel.scenes.game.minigames.boss;

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
	
	public WinScene(Game container) {
		super(container, "boss_win", Values.SCENE_BG_COLOR);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		mainSceneData = (MainSceneData) sceneData;
	}

	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		Helper.drawStringFromLeft(10, 100, "Platzhalter für die Win-Animation", Color.WHITE, 30, FONT.Chrobot, g2d, null, -1);
	}

	@Override
	public SceneData getDataForNextScene() {
		return mainSceneData;
	}

}
