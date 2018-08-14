package projekt.mathe.game.mathespiel.scenes.game.minigames.boss;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.ResLoader;

public class Parabola extends ScreenElement{

	private int[] xCoords, yCoords;
	private Projectile projectile;
	
	private boolean active;
	
	private static final Image aim = ResLoader.getImageByName("game/minigames/boss/aim.png");
	
	public Parabola(Scene container) {
		super(container, 0, 0, 0, 0);
		new Projectile(container, 0, 0);
	}

	public void calculateCoords(float xvector, float yvector, int frames) {
		int startX = 640;
		int startY = 470;
		projectile = new Projectile(getContainer(), startX, startY);
		projectile.launch(xvector, yvector);
		xCoords = new int[frames];
		yCoords = new int[frames];
		for(int i = 0; i < frames; i++) {
			projectile.onTick(1f);
			xCoords[i] = (int) projectile.getX();
			yCoords[i] = (int) projectile.getY();
		}
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public void onTick(float delta) {
		
	}
	
	@Override
	public void onPaint(Graphics2D g2d) {
		if(active) {
			for(int i = 1; i < xCoords.length; i++) {
				g2d.setColor(new Color(21, 12, 21));
				float stroke = (1f - (((float) i) / xCoords.length)) * (10f) + 1f;
				g2d.setStroke(new BasicStroke(stroke));
				g2d.drawLine(xCoords[i - 1], yCoords[i - 1], xCoords[i], yCoords[i]);
				g2d.setColor(new Color(0, 123, 255));
				g2d.setStroke(new BasicStroke(stroke / 2f));
				g2d.drawLine(xCoords[i - 1], yCoords[i - 1], xCoords[i], yCoords[i]);
			}
			int x = xCoords[xCoords.length - 1];
			int y = yCoords[yCoords.length - 1];
			if(y >= 40 && y <= 480) {
				g2d.drawImage(aim, x - 15, y - 15, null);
			}else {
				g2d.drawImage(aim, x - 10, y - 10, 20, 20, null);
			}
		}
	}
	
}
