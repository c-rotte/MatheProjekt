package projekt.mathe.game.mathespiel.scenes.game.pause;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.pause.PauseScreen;
import projekt.mathe.game.engine.pause.PauseScreenClickable;

public class MainPauseScreenClickable extends PauseScreenClickable{

	private Color bg1, bg2, bg3, textColor, currTextColor;
	private String text, currText;
	private int textSize, currTextSize;
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
		currText = text;
		this.textColor = textColor;
		currTextColor = textColor;
		this.textSize = textSize;
		currTextSize = textSize;
		hasText = true;
		return this;
	}
	
	public void setText(String text) {
		this.currText = text;
	}
	
	public void setTextColor(Color textColor) {
		this.currTextColor = textColor;
	}
	
	public void setTextSize(int textSize) {
		this.currTextSize = textSize;
	}
	
	@Override
	public void reset() {
		currText = text;
		currTextColor = textColor;
		currTextSize = textSize;
		super.reset();
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
			Helper.drawStringAroundPosition(getMiddle().x, getMiddle().y, currText, currTextColor, currTextSize, FONT.Ailerons, g2d, null, -1);
		}
	}

}
