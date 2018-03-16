package projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.GameScene;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;

public class PyramidScene extends GameScene{

	private PyramidGame pyramidGame;
	
	public PyramidScene(Game container) {
		super(container, "pyramid", Values.SCENE_BG_COLOR);
		pyramidGame = new PyramidGame(this);
		registerMiniGameMouseEvents(pyramidGame);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

	@Override
	public void onTick(float delta) {
		pyramidGame.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		pyramidGame.onPaint(g2d);
	}

	
	
}
