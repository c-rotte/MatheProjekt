package projekt.mathe.game.mathespiel.scenes.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.drawing.DrawingScreen;

public class DrawingScene extends Scene{

	private DrawingScreen screen;
	
	public DrawingScene(Game container) {
		super(container, "drawing", Color.CYAN);
		screen = new DrawingScreen(0, 0, 500, 300, 400);
		screen.setColor(Color.BLACK);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		
	}

	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		screen.onPaint(g2d);
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		screen.onMouseDragged(e);
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		screen.onMouseReleased(e);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		screen.onMousePressed(e);
	}
	
	@Override
	public SceneData getDataForNextScene() {
		return null;
	}

}
