package projekt.mathe.game.mathespiel.scenes.menu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.help.ResLoader;

public class CheckBox extends ScreenElement{

	private static Image x_Texture = ResLoader.getImageByName("clickables/x_texture.png");
	
	private String id;
	private boolean selected;
	private boolean clicked;
	private Color bg, fg;
	private Image tex;
	private String text;
	private Color textColor;
	
	public CheckBox(Scene container, String id, int x, int y, int a, Color bg, Color fg) {
		super(container, x, y, a, a);
		this.id = id;
		this.bg = bg;
		this.fg = fg;
		tex = Helper.colorImage(x_Texture, fg);
	}

	public CheckBox addText(String text, Color textColor) {
		this.text = text;
		this.textColor = textColor;
		return this;
	}
	
	public CheckBox setClicked(boolean clicked) {
		this.clicked = clicked;
		return this;
	}
	
	public String getID() {
		return id;
	}
	
	public boolean wasClicked() {
		return clicked;
	}
	
	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(bg);
		g2d.fill(getBounds());
		if(text != null) {
			Helper.drawStringFromLeft((int) (x + w + 20), (int) (y + h/2), text, textColor, (int) h, FONT.VCR, g2d, null, -1);
		}
		if(clicked) {
			g2d.setColor(fg);
			g2d.drawImage(tex, (int) x, (int) y, (int) w, (int) h, null);
		}
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		selected = getBounds().contains(e.getPoint()); 
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		selected = getBounds().contains(e.getPoint()); 
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		if(getBounds().contains(e.getPoint())) {
			clicked = !clicked;
		}
	}

}
