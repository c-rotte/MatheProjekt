package projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.ResLoader;

public class LooseBlock extends Dragable{

	private BlockHolder blockHolder;
	private boolean lastTickGrabbed;
	private UUID occupiedPlaceID;
	private float lastStillX, lastStillY;
	private Number number;
	private static Image brickImage1 = ResLoader.getImageByName("game/minigames/pyramid/brick1.png");
	private static Image brickImage2 = ResLoader.getImageByName("game/minigames/pyramid/brick2.png");
	private Image brickImage;
	private static final int KONTUR = 3;
	private boolean moveable;
	
	public LooseBlock(Scene container, int x, int y, int w, int h, BlockHolder blockHolder, int n1, int n2) {
		super(container, x, y, w, h);
		lastStillX = x;
		lastStillY = y;
		this.blockHolder = blockHolder;
		number = new Number(n1, n2, this);
		moveable = true;
		if(Math.random() < .55f) {
			brickImage = brickImage1;
		}else {
			brickImage = brickImage2;
		}
	}

	public void setMoveable(boolean moveable) {
		this.moveable = moveable;
	}
	
	public Number getNumber() {
		return number;
	}
	
	public boolean inPlace() {
		if(occupiedPlaceID == null) {
			return false;
		}
		return true;
	}
	
	public boolean inRightPlace() {
		if(occupiedPlaceID == null) {
			return false;
		}
		Place place = blockHolder.getPlaceByID(occupiedPlaceID);
		return place.getNumber().getN1() == number.getN1() && place.getNumber().getN2() == number.getN2();
	}
	
	public void setOccupiedPlaceID(UUID occupiedPlaceID) {
		this.occupiedPlaceID = occupiedPlaceID;
	}
	
	@Override
	public void onTick(float delta) {
		if(lastTickGrabbed) {
			lastTickGrabbed = false;
			Place place = blockHolder.getBoundsOfNearestPlace(this);
			if(place != null) {
				if(place.isOccupied()) {
					x = lastStillX;
					y = lastStillY;
				}else {
					x = place.x;
					y = place.y;
					place.setOccupied(true);
					occupiedPlaceID = place.getID();
				}
			}
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(Color.BLACK);
		g2d.fillRect((int) (x - KONTUR), (int) (y - KONTUR), (int) (w + KONTUR * 2), (int) (h + KONTUR * 2));
		g2d.drawImage(brickImage, (int) x, (int) y, (int) w, (int) h, null); 
		number.onPaint(g2d);
	}

	@Override
	public void onMousePressed(MouseEvent e) {
		if(!moveable) {
			return;
		}
		if(!blockHolder.grabbed()) {
			if(getBounds().contains(e.getPoint())) {
				if(occupiedPlaceID != null) {
					blockHolder.setPlaceOccupied(occupiedPlaceID, false);
					occupiedPlaceID = null;
				}
				lastStillX = x;
				lastStillY = y;
			}
			super.onMousePressed(e);
		}
	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		if(!moveable) {
			return;
		}
		lastTickGrabbed = wasGrabbed();
		super.onMouseReleased(e);
	}
	
}
