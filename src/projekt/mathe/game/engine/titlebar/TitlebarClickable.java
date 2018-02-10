package projekt.mathe.game.engine.titlebar;

import java.awt.Graphics2D;
import java.awt.Image;

public abstract class TitlebarClickable {

	public int x, y, w, h;
	public int bx, by, bw, bh;
	private Image texture, hover;
	private boolean selected;
	
	public TitlebarClickable(int x, int y, int w, int h, int bx, int by, int bw, int bh, Image texture, Image hover) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.bx = bx;
		this.by = by;
		this.bw = bw;
		this.bh = bh;
		this.texture = texture;
		this.hover = hover;
	}
	
	private boolean selected(int mx, int my) {
		if(mx > x && mx < x + w) {
			if(my > y && my < y + h) {
				return true;
			}
		}
		return false;
	}
	
	public void checkSelected(int mx, int my, boolean pressed) {
		this.selected = selected(mx, my);
		if(pressed && selected) {
			onClick();
		}
	}
	
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public abstract void onClick();
	
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(selected ? hover : texture, bx, by, bw, bh, null);
	}
	
}
