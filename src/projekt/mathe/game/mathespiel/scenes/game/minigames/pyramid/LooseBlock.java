package projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.UUID;

import projekt.mathe.game.engine.Scene;

public class LooseBlock extends Dragable{

	private BlockHolder blockHolder;
	private boolean lastTickGrabbed;
	private UUID occupiedPlaceID;
	private float lastStillX, lastStillY;
	private Number number;
	
	public LooseBlock(Scene container, int x, int y, int w, int h, BlockHolder blockHolder, float value) {
		super(container, x, y, w, h);
		lastStillX = x;
		lastStillY = y;
		this.blockHolder = blockHolder;
		createNumber(value);
	}

	public LooseBlock(Scene container, int x, int y, int w, int h, BlockHolder blockHolder, int n1, int n2) {
		super(container, x, y, w, h);
		lastStillX = x;
		lastStillY = y;
		this.blockHolder = blockHolder;
		number = new Number(n1, n2, this);
	}
	
	private void createNumber(float value) {
		float tol = 0.001f;
		float h1 = 1;
		float h2 = 0;
		float k1 = 0;
		float k2 = 1;
		float b = value;
		do {
			float a = (float) Math.floor(b);
			float au = h1;
			h1 = a * h1 + h2;
			h2 = au;
			au = k1;
			k1 = a * k1 + k2;
			k2 = au;
			b = 1 / (b - a);
		} while (Math.abs(value - h1/k1) > value * tol);
		number = new Number((int) h1, (int) k1, this);
	}
	
	public boolean inRightPlace() {
		if(occupiedPlaceID == null) {
			return false;
		}
		Place place = blockHolder.getPlaceByID(occupiedPlaceID);
		return place.getNumber().getN1() == number.getN1() && place.getNumber().getN2() == number.getN2();
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
		g2d.setColor(Color.RED);
		g2d.fill(getBounds());
		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(5f));
		g2d.draw(getBounds());
		number.onPaint(g2d);
	}

	@Override
	public void onMousePressed(MouseEvent e) {
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
		lastTickGrabbed = wasGrabbed();
		super.onMouseReleased(e);
	}
	
}
