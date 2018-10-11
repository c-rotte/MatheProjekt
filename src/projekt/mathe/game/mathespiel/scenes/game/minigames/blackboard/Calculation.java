package projekt.mathe.game.mathespiel.scenes.game.minigames.blackboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.elements.ClickElement;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;

public class Calculation extends ClickElement{

	private String calc;
	private boolean selected;
	private int f1, f2, f3;
	private char c;
	private boolean right;
	
	public Calculation(Scene container, int x, int y, int f1, int f2, int f3, char c) {
		super(container, x, y, 200, 100);
		this.f1 = f1;
		this.f2 = f2;
		this.f3 = f3;
		this.c = c;
		switch (c) {
			case '+' : right = f1 + f2 == f3; break;
			case '-' : right = f1 - f2 == f3; break;
			case '*' : right = f1 * f2 == f3; break;
		}
		if(c == '*') {
			c = '•';
		}
		calc = (f1 >= 0 ? f1 : "(" + f1 + ")") + " " + c + " " + (f2 >= 0 ? f2 : "(" + f2 + ")") + " = " + f3;
		//calc = f1 + " " + c + " " + f2 + " = " + f3;		
	}

	public boolean isSelected() {
		return selected;
	}
	
	public boolean isRight() {
		return right;
	}
	
	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(selected ? new Color(255, 255, 255, 100) : Color.WHITE);
		g2d.setStroke(new BasicStroke(5));
		g2d.draw(getBounds());
		
		RenderingHints renderingHints = g2d.getRenderingHints();
		g2d.setRenderingHints(Values.SMOOTH_RENDERING_HINTS);
		Helper.drawStringAroundPosition(getMiddle().x, getMiddle().y, calc, g2d.getColor(), 19, FONT.VCR, g2d, null, -1);
		g2d.setRenderingHints(renderingHints);
	}

	@Override
	public void onClicked(MouseEvent e) {
		selected = !selected;
	}

	@Override
	public void onMisClicked(MouseEvent e) {
		
	}
	
	public static final Calculation generateInstance(Scene container, int x, int y, boolean right) {
		int f1 = ThreadLocalRandom.current().nextInt(-9, 10);
		int f2 = ThreadLocalRandom.current().nextInt(-9, 10);
		int f3 = 0;
		char c = '?';
		switch (ThreadLocalRandom.current().nextInt(0, 3)) {
			case 0 :
				c = '+';
				f3 = f1 + f2;
				break;
			case 1 :
				c = '-';
				f3 = f1 - f2;
				break;
			case 2 :
				c = '*';
				f3 = f1 * f2;
				break;
		}
		if(right) {
			return new Calculation(container, x, y, f1, f2, f3, c);
		}else {
			switch (ThreadLocalRandom.current().nextInt(0, 5)) {
				case 0 :
					return new Calculation(container, x, y, f1, f3, f2, c);
				case 1 :
					return new Calculation(container, x, y, f2, f1, f3, c);
				case 2 :
					return new Calculation(container, x, y, f2, f3, f1, c);
				case 3 :
					return new Calculation(container, x, y, f3, f1, f2, c);
				case 4 :
					return new Calculation(container, x, y, f3, f2, f1, c);
			}
		}
		return null;
	}
	
}
