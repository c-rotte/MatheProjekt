package projekt.mathe.game.engine.titlebar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Frame;
import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.mathespiel.Settings;

public class Titlebar {

	private String title;
	
	public Frame frame;
	public Game container;
	
	private int currMPressX, currMPressY;
	private Image icon;
	
	private CloseButton closeButton;
	private MinimizeButton minimizeButton;
	private MaximizeButton maximizeButton;
	
	public Titlebar(String title, Frame frame, Game container){
		this.title = title;
		this.frame = frame;
		this.container = container;
		closeButton = new CloseButton(Values.WINDOW_WIDTH - Values.TITLEBAR_HEIGHT, 0, Values.TITLEBAR_HEIGHT, Values.TITLEBAR_HEIGHT, this);
		maximizeButton = new MaximizeButton(Values.WINDOW_WIDTH - Values.TITLEBAR_HEIGHT * 2, 0, Values.TITLEBAR_HEIGHT, Values.TITLEBAR_HEIGHT, this);
		minimizeButton = new MinimizeButton(Values.WINDOW_WIDTH - Values.TITLEBAR_HEIGHT * 3, 0, Values.TITLEBAR_HEIGHT, Values.TITLEBAR_HEIGHT, this);
		currMPressX = -1;
		currMPressY = -1;
	}

	public void setImage(Image icon) {
		this.icon = icon;
	}
	
	public void onPaint(Graphics2D g2d){
		g2d.setColor(container.container.focussed() ? Values.TITLEBAR_COLOR_2 : Color.LIGHT_GRAY);
		g2d.fillRect(0, 0, Values.WINDOW_WIDTH, Values.TITLEBAR_HEIGHT);
		closeButton.onPaint(g2d);
		maximizeButton.onPaint(g2d);
		minimizeButton.onPaint(g2d);
		if(icon != null) {
			g2d.drawImage(icon, Values.TITLEBAR_HEIGHT/4, Values.TITLEBAR_HEIGHT/4, Values.TITLEBAR_HEIGHT/2, Values.TITLEBAR_HEIGHT/2, null);
		}
		Helper.drawStringAroundPosition(Values.WINDOW_WIDTH/2, Values.TITLEBAR_HEIGHT/2, title, Values.TITLEBAR_COLOR_1, Values.TITLEBAR_HEIGHT - 9, FONT.Ailerons, g2d, null, -1);
	}
	
	public void onMouseClicked(MouseEvent e){
		closeButton.checkSelected(e.getX(), e.getY(), true);
		maximizeButton.checkSelected(e.getX(), e.getY(), true);
		minimizeButton.checkSelected(e.getX(), e.getY(), true);
	}
	
	public void onMousePressed(MouseEvent e) {
		if(e.getY() <= Values.TITLEBAR_HEIGHT && e.getX() < Values.WINDOW_WIDTH - Values.TITLEBAR_HEIGHT * 2) {
			currMPressX = e.getX();
			currMPressY = e.getY();
		}else {
			currMPressX = -1;
			currMPressY = -1;
		}
	}
	
	public void onMouseMoved(MouseEvent e) {
		closeButton.checkSelected(e.getX(), e.getY(), false);
		maximizeButton.checkSelected(e.getX(), e.getY(), false);
		minimizeButton.checkSelected(e.getX(), e.getY(), false);
	}
	
	public void onMouseDragged(MouseEvent e){
		closeButton.checkSelected(e.getX(), e.getY(), false);
		maximizeButton.checkSelected(e.getX(), e.getY(), false);
		minimizeButton.checkSelected(e.getX(), e.getY(), false);
		if(currMPressX != -1 && currMPressY != -1){
			frame.getJFrame().setLocation(frame.getJFrame().getLocation().x + e.getX() - currMPressX, frame.getJFrame().getLocation().y + e.getY() - currMPressY);
		}
	}
	
	public void onMouseExited() {
		minimizeButton.setSelected(false);
		maximizeButton.setSelected(false);
		closeButton.setSelected(false);
	}
	
}
