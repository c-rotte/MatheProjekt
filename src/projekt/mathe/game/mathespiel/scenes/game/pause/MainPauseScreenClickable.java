package projekt.mathe.game.mathespiel.scenes.game.pause;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.pause.PauseScreen;
import projekt.mathe.game.engine.pause.PauseScreenClickable;

public class MainPauseScreenClickable extends PauseScreenClickable{

	private Color bg1, bg2, bg3, textColor;
	private String text;
	private int textSize;
	private boolean hasText;
	
	public MainPauseScreenClickable(Scene container, PauseScreen pauseScreen, int x, int y, int w, int h, Color bg1, Color bg2, Color bg3, Runnable runnable) {
		super(container, pauseScreen, x, y, w, h);
		this.bg1 = bg1;
		this.bg2 = bg2;
		this.bg3 = bg3;
		addRunnable(runnable);
	}

	public MainPauseScreenClickable addText(String text, int textSize, Color textColor) {
		this.text = text;
		this.textColor = textColor;
		this.textSize = textSize;
		hasText = true;
		return this;
	}
	
	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		switch(getState()) {
			case "normal" : g2d.setColor(bg1); break;
			case "selected" : g2d.setColor(bg2); break;
			case "clicked" : g2d.setColor(bg3); break;
		}
		g2d.fill(getBounds());
		if(hasText) {
			Helper.drawStringAroundPoint(getMiddle().x, getMiddle().y, text, textColor, textSize, FONT.Ailerons, g2d);
		}
	}

}
