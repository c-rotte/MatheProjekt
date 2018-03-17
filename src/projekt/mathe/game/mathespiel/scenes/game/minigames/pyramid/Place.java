package projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.util.UUID;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.help.TextureHelper;

public class Place extends ScreenElement{

	private boolean occupied;
	private UUID id;
	private Number number;
	
	public Place(Scene container, int x, int y, int w, int h, int n1, int n2) {
		super(container, x, y, w, h);
		id = UUID.randomUUID();
		number = new Number(n1, n2, null);
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
		
	}

	public boolean isOccupied() {
		return occupied;
	}

	public void setOccupied(boolean occupied) {
		this.occupied = occupied;
	}

}
