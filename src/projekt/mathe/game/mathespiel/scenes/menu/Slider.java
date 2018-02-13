package projekt.mathe.game.mathespiel.scenes.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;

public class Slider extends ScreenElement{

	private String text;
	private Color textColor, bgColor, bgColorSelected, bgColorClicked;
	private float aimX;
	private int textSize;
	private boolean selected, clicked;
	private static final int SPEED = 7;
	private Runnable runnable;
	private boolean interactable;
	private SliderHolder holder;
	private int originalX;
	
	public Slider(Scene container, SliderHolder holder, int x, int aimX, int y, int w, int h, String text, int textSize, Color textColor, Color bgColor, Color bgColorSelected, Color bgColorClicked, Runnable runnable) {
		super(container, x, y, w, h);
		originalX = x;
		this.holder = holder;
		this.aimX = aimX;
		this.textSize = textSize;
		this.text = text;
		this.textColor = textColor;
		this.bgColor = bgColor;
		this.bgColorSelected = bgColorSelected;
		this.bgColorClicked = bgColorClicked;
		this.runnable = runnable;
		interactable = true;
	}
	
	public void reset() {
		selected = false;
		clicked = false;
		x = originalX;
		interactable = true;
	}
	
	public void setInteractable(boolean interactable) {
		this.interactable = interactable;
	}
	
	public boolean isInteractable() {
		return interactable;
	}
	
	public void addOnClickListener(Runnable runnable) {
		this.runnable = runnable;
	}
	
	@Override
	public void onMouseDragged(MouseEvent e){
		if(!clicked) {
			selected = getBounds().contains(e.getPoint());
		}
	}
	
	@Override
	public void onMouseMoved(MouseEvent e){
		if(!clicked) {
			selected = getBounds().contains(e.getPoint());
		}
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		if(selected && interactable && !holder.wasClicked()) {
			interactable = false;
			clicked = true;
			runnable.run();
		}
	}
	
	@Override
	public void onTick(float delta) {
		if(!clicked && x < aimX) {
			x += delta * SPEED;
			if(x > aimX) {
				x = aimX;
			}
		}else if(clicked && x > -w) {
			x -= delta * SPEED;
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		if(selected && !holder.wasClicked()) {
			if(clicked) {
				g2d.setColor(bgColorClicked);
			}else {
				g2d.setColor(bgColorSelected);
			}
		}else {
			g2d.setColor(bgColor);
		}
		if(!interactable) {
			g2d.setColor(bgColorClicked);
		}
		g2d.fillRect((int) x, (int) y, (int) w, (int) h);
		Helper.drawStringAroundPoint((int) (x + w/2), (int) (y + h/2), text, textColor, textSize, FONT.Ailerons, g2d);
	}

}
