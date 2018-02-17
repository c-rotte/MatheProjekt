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

public class Slider extends ScreenElement{

	private SliderHolder holder;
	private Runnable runnable;
	private String state;
	private Image[] tex;
	
	public Slider(Scene container, SliderHolder holder, int x, int y, int w, int h, Image[] tex) {
		super(container, x, y, w, h);
		this.holder = holder;
		state = "normal";
		this.tex = tex;
	}
	
	public void reset() {
		state = "normal";
	}
	
	public Slider addOnClickListener(Runnable runnable) {
		this.runnable = runnable;
		return this;
	}
	
	public String getState() {
		return state;
	}
	
	@Override
	public void onMouseDragged(MouseEvent e){
		if(!state.equals("clicked") && !holder.wasClicked()) {
			if(getBounds().contains(e.getPoint())) {
				state = "selected";
			}else {
				state = "normal";
			}
		}
	}
	
	@Override
	public void onMouseMoved(MouseEvent e){
		if(!state.equals("clicked") && !holder.wasClicked()) {
			if(getBounds().contains(e.getPoint())) {
				state = "selected";
			}else {
				state = "normal";
			}
		}
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		if(getBounds().contains(e.getPoint()) && !holder.wasClicked()) {
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
		switch (state) {
			case "normal": g2d.drawImage(tex[0], (int) x, (int) y, (int) w, (int) h, null); break;
			case "selected": g2d.drawImage(tex[1], (int) x, (int) y, (int) w, (int) h, null); break;
			case "clicked": g2d.drawImage(tex[2], (int) x, (int) y, (int) w, (int) h, null); break;
		}
		
	}

}
