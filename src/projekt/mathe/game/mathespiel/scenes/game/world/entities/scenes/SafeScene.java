package projekt.mathe.game.mathespiel.scenes.game.world.entities.scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;

public class SafeScene extends Scene{

	private MainSceneData mainSceneData;
	private static final Image bg = ResLoader.getImageByName("game/entities/safe_bg.jpg");
	private PasswordInput passwordInput;
	
	public SafeScene(Game container) {
		super(container, "safe", Values.SCENE_BG_COLOR);
		passwordInput = new PasswordInput(this);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		mainSceneData = (MainSceneData) sceneData;
		passwordInput.reset();
	}

	@Override
	public void onTick(float delta) {
		passwordInput.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(bg, 0, 0, null);
		passwordInput.onPaint(g2d);
	}

	@Override
	public SceneData getDataForNextScene() {
		return mainSceneData;
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		passwordInput.onMouseDragged(e);
		super.onMouseDragged(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		passwordInput.onMouseMoved(e);
		super.onMouseMoved(e);
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		passwordInput.onMouseReleased(e);
		super.onMouseReleased(e);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		passwordInput.onMousePressed(e);
		super.onMousePressed(e);
	}
	
}
