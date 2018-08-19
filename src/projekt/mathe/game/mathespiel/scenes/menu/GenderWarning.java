package projekt.mathe.game.mathespiel.scenes.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;

public class GenderWarning extends Warning{

	private String[] lines = {
		"Bist du ein Mädchen",
		"oder ein Junge?"
	};
	private ButtonHolder buttonHolder;
	private String gender;
	
	public GenderWarning(Scene container) {
		super(container, 340, 150, 600, 400);
		buttonHolder = new ButtonHolder(container);
		buttonHolder.addElement(new Button(container, buttonHolder, 395, 448, 140, 35)
				.setText("MÄDCHEN", 40, 465, 462, new Color[] {new Color(134, 0, 168), new Color(106, 2, 132), new Color(69, 2, 86)}, 3.4f)
				.addOnClickListener(new Runnable() {
					@Override
					public void run() {
						gender = "girl";
						close();
					}
				}));
		buttonHolder.addElement(new Button(container, buttonHolder, 680, 448, 220, 35)
				.setText("JUNGE", 40, 780, 462, new Color[] {new Color(0, 119, 239), new Color(1, 98, 196), new Color(2, 69, 137)}, 3.4f)
				.addOnClickListener(new Runnable() {
					@Override
					public void run() {
						gender = "boy";
						close();
					}
				}));
	}

	public void reset() {
		gender = null;
		buttonHolder.reset();
	}
	
	public String getGender() {
		return gender;
	}
	
	@Override
	public void onTick(float delta) {
		buttonHolder.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(new Color(20, 20, 20, 255));
		g2d.fill(getBounds());
		g2d.setStroke(new BasicStroke(10f));
		g2d.setColor(Color.DARK_GRAY);
		g2d.draw(getBounds());
		for(int i = 0; i < lines.length; i++) {
			Helper.drawStringAroundPosition(640, 230 + i * 38, lines[i].toUpperCase(), Color.WHITE, 39, FONT.VCR, g2d, null, -1);
		}
		buttonHolder.onPaint(g2d);
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		buttonHolder.onMouseClicked(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		buttonHolder.onMouseMoved(e);
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		buttonHolder.onMouseDragged(e);
	}
	
}
