package projekt.mathe.game.mathespiel.scenes.game.minigames.blackboard;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.help.TextureHelper;

public class Schwamm extends ScreenElement{

	private static Image w1 = ResLoader.getImageByName("game/minigames/board/water_1.png");
	private static Image w2 = ResLoader.getImageByName("game/minigames/board/water_2.png");
	
	private static Image schwamm = ResLoader.getImageByName("game/minigames/board/schwamm.png");
	
	private TextureHelper waterDrops;
	private float dropFrames;
	private Point currentDrops;
	
	private static final int waterFrames = 5;
	
	public Schwamm(Scene container, int x, int y) {
		super(container, x, y, 50, 46);
		waterDrops = new TextureHelper();
		waterDrops.addState("normal", waterFrames, w1, w2);
		dropFrames = 0;
	}

	@Override
	public void onTick(float delta) {
		waterDrops.onTick(delta);
		if(dropFrames >= waterFrames * 2) {
			currentDrops = null;
		}else if(currentDrops != null){
			dropFrames += delta;
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		if(currentDrops != null){
			g2d.drawImage(waterDrops.getCurrentImage(), currentDrops.x - 100, currentDrops.y - 57, 200, 114, null);
		}
		g2d.drawImage(schwamm, (int) (getX() - getW() / 2), (int) (getY() - getH() / 2), (int) getW(), (int) getH(), null);
	}

	@Override
	public void onMouseMoved(MouseEvent e) {
		setX(e.getX());
		setY(e.getY());
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		setX(e.getX());
		setY(e.getY());
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
	
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		currentDrops = new Point(e.getX(), e.getY());
		dropFrames = 0;
		waterDrops.resetCurrentState();
	}
	
}
