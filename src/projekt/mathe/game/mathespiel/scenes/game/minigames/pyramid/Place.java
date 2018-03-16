package projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.util.UUID;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;

public class Place extends ScreenElement{

	private boolean occupied;
	private UUID id;
	private Number number;
	
	public Place(Scene container, int x, int y, int w, int h, int n1, int n2) {
		super(container, x, y, w, h);
		id = UUID.randomUUID();
		number = new Number(n1, n2, null);
	}

	public Place(Scene container, int x, int y, int w, int h, float value) {
		super(container, x, y, w, h);
		id = UUID.randomUUID();
		createNumber(value);
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
		number = new Number((int) h1, (int) k1, null);
	}
	
	public Number getNumber() {
		return number;
	}
	
	public UUID getID() {
		return id;
	}
	
	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(Color.GREEN);
		g2d.fill(getBounds());
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

}
