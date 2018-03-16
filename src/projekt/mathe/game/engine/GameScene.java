package projekt.mathe.game.engine;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import projekt.mathe.game.engine.minigame.MiniGame;

public abstract class GameScene extends Scene{

	private MiniGame miniGame;
	
	public GameScene(Game container, String id, Color backgroundcolor) {
		super(container, id, backgroundcolor);
	}

	public void registerMiniGameMouseEvents(MiniGame miniGame) {
		this.miniGame = miniGame;
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		if(miniGame != null) {
			miniGame.onMouseClicked(e);
		}
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		if(miniGame != null) {
			miniGame.onMouseDragged(e);
		}
	}
	
	@Override
	public void onMouseExited(MouseEvent e) {
		if(miniGame != null) {
			miniGame.onMouseExited(e);
		}
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		if(miniGame != null) {
			miniGame.onMouseMoved(e);
		}
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		if(miniGame != null) {
			miniGame.onMousePressed(e);
		}
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		if(miniGame != null) {
			miniGame.onMouseReleased(e);
		}
	}
	
	@Override
	public void onMouseWheelMoved(MouseWheelEvent e) {
		if(miniGame != null) {
			miniGame.onMouseWheelMoved(e);
		}
	}
	
}
