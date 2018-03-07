package projekt.mathe.game.mathespiel.scenes.menu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;

public class NewGameWarning extends Warning{

	private ButtonHolder buttonHolder;
	private String state;
	private static final String[] warningMessage = {
		"Du hast bereits ein Spiel",
		"begonnen. Wenn du ein",
		"neues erstellst, wird der",
		"vorherige Stand gelöscht.",
		"Möchtest du fortfahren?"
	};
	
	public NewGameWarning(Scene container) {
		super(container, 340, 150, 600, 400);
		buttonHolder = new ButtonHolder(container);
		buttonHolder.addElement(new Button(container, buttonHolder, 395, 448, 140, 35)
				.setText("WEITER", 40, 465, 462, new Color[] {new Color(0, 200, 75), new Color(0, 100, 141), new Color(0, 55, 78)}, 3.4f)
				.addOnClickListener(new Runnable() {
					@Override
					public void run() {
						state = "continued";
						close();
					}
				}));
		buttonHolder.addElement(new Button(container, buttonHolder, 670, 448, 220, 35)
				.setText("ABBRECHEN", 40, 780, 462, new Color[] {new Color(200, 30, 0), new Color(0, 100, 141), new Color(0, 55, 78)}, 3.4f)
				.addOnClickListener(new Runnable() {
					@Override
					public void run() {
						state = "cancelled";
						close();
					}
				}));
		state = "nothing";
	}

	public void reset() {
		state = "nothing";
		buttonHolder.reset();
	}
	
	public String getState() {
		return state;
	}
	
	@Override
	public void onTick(float delta) {
		
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
	
	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.setColor(new Color(20, 20, 20, 255));
		g2d.fill(getBounds());
		g2d.setStroke(new BasicStroke(10f));
		g2d.setColor(Color.DARK_GRAY);
		g2d.draw(getBounds());
		Helper.drawStringAroundPosition(640, 185, "ACHTUNG!", Color.RED, 50, FONT.VCR, g2d, null, -1);
		for(int i = 0; i < warningMessage.length; i++) {
			Helper.drawStringFromLeft(355, 230 + i * 38, warningMessage[i].toUpperCase(), Color.RED, 39, FONT.VCR, g2d, null, -1);
		}
		buttonHolder.onPaint(g2d);
	}

}
