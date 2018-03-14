package projekt.mathe.game.mathespiel.scenes.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.ImageFilter;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.mathespiel.Settings;

public class Button extends ScreenElement{

	private ButtonHolder holder;
	private Runnable runnable;
	private String state;
	private Color[] colors;
	private String text;
	private int size;
	private float outlineSize;
	
	private int textX, textY;
	
	public Button(Scene container, ButtonHolder holder, int x, int y, int w, int h) {
		super(container, x, y, w, h);
		this.holder = holder;
		state = "normal";
	}
	
	public Button setText(String text, int size, int x, int y, Color[] colors, float outlineSize) {
		this.colors = colors;
		this.size = size;
		this.text = text;
		this.outlineSize = outlineSize;
		textX = x;
		textY = y;
		return this;
	}
	
	public void reset() {
		state = "normal";
	}
	
	public Button addOnClickListener(Runnable runnable) {
		this.runnable = runnable;
		return this;
	}
	
	public String getState() {
		return state;
	}
	
	@Override
	public void onMouseDragged(MouseEvent e){
		if(!state.equals("clicked") && !holder.wasClicked() && !getContainer().fading) {
			if(getBounds().contains(e.getPoint())) {
				state = "selected";
			}else {
				state = "normal";
			}
		}
	}
	
	@Override
	public void onMouseMoved(MouseEvent e){
		if(!state.equals("clicked") && !holder.wasClicked() && !getContainer().fading) {
			if(getBounds().contains(e.getPoint())) {
				state = "selected";
			}else {
				state = "normal";
			}
		}
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		if(getBounds().contains(e.getPoint()) && !holder.wasClicked() && !getContainer().fading) {
			state = "clicked";
			if(runnable != null) {
				runnable.run();
			}
		}
	}
	
	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(Color.GRAY);
		//g2d.fill(getBounds());
		switch (state) {
			case "normal": Helper.drawStringAroundPosition(textX, textY, text, Settings.DARKMODE ? Color.BLACK : Color.WHITE, size, FONT.VCR, g2d, colors[0], outlineSize); break;
			case "selected": Helper.drawStringAroundPosition(textX, textY, text, Settings.DARKMODE ? Color.BLACK : Color.WHITE, size, FONT.VCR, g2d, colors[1], outlineSize); break;
			case "clicked": Helper.drawStringAroundPosition(textX, textY, text, Settings.DARKMODE ? Color.BLACK : Color.WHITE, size, FONT.VCR, g2d, colors[2], outlineSize); break;
		}
		
	}

}
