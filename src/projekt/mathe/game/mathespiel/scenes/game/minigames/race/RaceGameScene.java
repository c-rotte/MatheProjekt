package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.Main;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;

public class RaceGameScene extends Scene{

	private RaceGame raceGame;
	private MainSceneData mainSceneData;
	
	public RaceGameScene(Game container) {
		super(container, "race", Values.SCENE_BG_COLOR);
		registerWorld(new RaceWorld(this));
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		camera.setX(0);
		camera.setY(0);
		raceGame = new RaceGame(this);
		mainSceneData = (MainSceneData) sceneData;
	}

	@Override
	public void onTick(float delta) {
		raceGame.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		raceGame.onPaint(g2d);
	}

	@Override
	public SceneData getDataForNextScene() {
		return mainSceneData;
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		raceGame.onMouseClicked(e);
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		raceGame.onMouseDragged(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		raceGame.onMouseMoved(e);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		raceGame.onMousePressed(e);
	}
	
	
	
}
