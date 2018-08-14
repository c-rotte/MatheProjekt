package projekt.mathe.game.mathespiel.scenes.game.minigames.boss;

import java.awt.Color;
import java.awt.Graphics2D;

import javax.xml.bind.ValidationEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.GameScene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class BossScene extends GameScene{

	private MainSceneData mainSceneData;
	private BossGame bossGame;
	
	public BossScene(Game container) {
		super(container, "boss", Values.SCENE_BG_COLOR);
		registerWorld(World.emptyInstance(this));
		bossGame = new BossGame(this);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		mainSceneData = (MainSceneData) sceneData;
		bossGame = new BossGame(this);
		registerMiniGameMouseEvents(bossGame);
	}

	@Override
	public void onTick(float delta) {
		bossGame.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		bossGame.onPaint(g2d);
	}

	@Override
	public SceneData getDataForNextScene() {
		return mainSceneData;
	}

}
