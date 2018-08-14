package projekt.mathe.game.mathespiel.scenes.game.minigames.angle;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.help.ResLoader;

public class AngleCalculator extends ScreenElement{

	private static final Image pizza = ResLoader.getImageByName("game/minigames/angle/pizza.png");
	private static final Image table = ResLoader.getImageByName("game/minigames/angle/tisch.png");
	
	private float chosenAngle;
	private Point anglePoint;
	private float correctAngle;
	private Point correctAnglePoint;
	private Point lastMousePoint;
	
	private Animator finishAnimator;
	private boolean finished;
	
	public AngleCalculator(Scene container, int x, int y) {
		super(container, x - 500, y - 500, 1000, 500);
		chosenAngle = 90f;
		anglePoint = calculateAnglePoint(chosenAngle);
		finishAnimator = new Animator(180, 1);
	}

	public float getCorrectAngle() {
		return correctAngle;
	}
	
	public float getChosenAngle() {
		return chosenAngle;
	}
	
	private Point calculateAnglePoint(float angle) {
		float x = getX() + getW() / 2;
		float y = getY() + getH();
		x += getH() * Math.cos(Math.toRadians(angle));
		y -= getH() * Math.sin(Math.toRadians(angle));
		return new Point((int) x, (int) y);
	}
	
	private void drawAngle(Graphics2D g2d, Color color, float a, Point p, float stroke) {
		Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 80);
		g2d.setColor(color2);
		g2d.fillArc((int) getX(), (int) getY(), (int) getW(), (int) (getH() * 2), 0, Math.round(a));
		g2d.setColor(color);
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawArc((int) getX(), (int) getY(), (int) getW(), (int) (getH() * 2), 0, Math.round(a));
		g2d.setStroke(new BasicStroke(stroke + 2f));
		g2d.drawLine((int) (getX() + getW() / 2), (int) (getY() + getH()), p.x, p.y);
		g2d.setColor(color);
		g2d.fillRect((int) (getX() + getW() / 2), (int) (getY() + getH() - (stroke + 2f) / 2), (int) (getW() / 2), (int) (stroke + 2f));
	}
	
	public void setCorrectAngle(float f, boolean finished) {
		correctAngle = f;
		if(finished) {
			this.finished = true;
			correctAnglePoint = calculateAnglePoint(correctAngle);
			finishAnimator.reset();
		}
	}
	
	public void reset() {
		correctAngle = 0f;
		correctAnglePoint = null;
		finished = false;
	}
	
	public boolean chosenWasRight() {
		return Math.abs(chosenAngle - correctAngle) <= 10;
	}
	
	public boolean finishedAnimation() {
		return finishAnimator.finished();
	}
	
	@Override
	public void onTick(float delta) {
		if(finished) {
			finishAnimator.calculate(delta);
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(table, 0, 0, null);
		g2d.drawImage(pizza, (int) getX() - 50, (int) getY() - 50 + 10, null);
		g2d.setColor(Color.DARK_GRAY);
		g2d.fillRect((int) (getX() - 7), (int) (getY() + getH() - 7), (int) (getW() + 15), 15);
		g2d.setStroke(new BasicStroke(15f));
		g2d.drawArc((int) getX(), (int) getY(), (int) getW(), (int) (getH() * 2), 0, 180);
		g2d.setColor(Color.LIGHT_GRAY);
		g2d.fillRect((int) (getX() - 2), (int) (getY() + getH() - 2), (int) (getW() + 5), 5);
		g2d.setStroke(new BasicStroke(4f));
		g2d.drawArc((int) getX(), (int) getY(), (int) getW(), (int) (getH() * 2), 0, 180);		
		drawAngle(g2d, Color.GRAY, chosenAngle, anglePoint, 5f);
		if(correctAnglePoint != null && finished) {
			Composite composite = g2d.getComposite();
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, finishAnimator.getCurrValueRelative()));
			drawAngle(g2d, new Color(0, 255, 20), correctAngle, correctAnglePoint, 5f);
			g2d.setComposite(composite);
		}
		g2d.setColor(Color.BLACK);
		g2d.fillOval((int) (getX() + getW() / 2 - 12), (int) (getY() + getH() - 12), 24, 24);
		Helper.drawStringFromLeft(130, 630 , "180°", Color.WHITE, 35, FONT.VCR, g2d, Color.BLACK, 5f);
		Helper.drawStringFromLeft(1110, 630, "0°", Color.WHITE, 35, FONT.VCR, g2d, Color.BLACK, 5f);
		if(correctAngle > 0) {
			Helper.drawStringAroundPosition(640, 630, "Gesuchter Winkel: " + (int) correctAngle + "°", Color.WHITE, 35, FONT.VCR, g2d, Color.BLACK, 5f);
		}
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		if(lastMousePoint != null) {
			float xDiff = (float) (e.getX() - lastMousePoint.getX());
			chosenAngle -= xDiff * 0.17f;
			float yDiff = (float) (e.getY() - lastMousePoint.getY());
			if(chosenAngle <= 90) {
				chosenAngle -= yDiff * 0.15f;	
			}else {
				chosenAngle += yDiff * 0.15f;	
			}
			if(chosenAngle < 0) {
				chosenAngle = 0;
			}else if(chosenAngle > 180) {
				chosenAngle = 180;
			}
			anglePoint = calculateAnglePoint(chosenAngle);
		}
		lastMousePoint = e.getPoint();
		super.onMouseDragged(e);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		lastMousePoint = e.getPoint();
		super.onMousePressed(e);
	}
	
}
