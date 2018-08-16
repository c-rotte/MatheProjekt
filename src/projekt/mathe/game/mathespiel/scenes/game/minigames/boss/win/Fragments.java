package projekt.mathe.game.mathespiel.scenes.game.minigames.boss.win;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.util.concurrent.ThreadLocalRandom;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.mathespiel.scenes.game.minigames.boss.ShapeGenerator;

public class Fragments extends ScreenElement{

	private TriangleHolder triangleHolder;
	
	public Fragments(Scene container, int x, int y) {
		super(container, x, y, 0, 0);
		triangleHolder = new TriangleHolder(container);
		for(int i = 0; i < 20; i++) {
			float factor = 2f;
			float vecX = (ThreadLocalRandom.current().nextFloat() + 0.1f) * factor;
			float vecY = (0.5f - ThreadLocalRandom.current().nextFloat()) * factor;
			triangleHolder.addElement(new Triangle(container, createTriangle(30, 30), x, y, vecX, vecY));
		}
	}

	@Override
	public void onTick(float delta) {
		triangleHolder.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		triangleHolder.onPaint(g2d);
	}
	
	public static final Polygon createTriangle(int w, int h) {
		Point p1 = new Point(ThreadLocalRandom.current().nextInt(0, w + 1), ThreadLocalRandom.current().nextInt(0, h / 2 - 1));
		Point p2 = new Point(ThreadLocalRandom.current().nextInt(0, w / 2), h);
		Point p3 = new Point(ThreadLocalRandom.current().nextInt(w / 2, w + 1), h);
		int[] x = {p1.x, p2.x, p3.x};
		int[] y = {p1.y, p2.y, p3.y};
		return new Polygon(x, y, 3);
	}

	public class TriangleHolder extends Holder<Triangle> {

		public TriangleHolder(Scene container) {
			super(container);
		}
		
	}
	
	public class Triangle extends ScreenElement {

		private Polygon polygon;
		
		private float vecX, vecY;
		private float randomRotation;
		private float rotation;
		
		private Animator boostAnimator;
		
		public Triangle(Scene container, Polygon polygon, int x, int y, float vecX, float vecY) {
			super(container, x, y, 0, 0);
			this.polygon = polygon;
			this.vecX = vecX;
			this.vecY = vecY;
			randomRotation = (0.5f - ThreadLocalRandom.current().nextFloat()) * 0.05f;
			rotation = ThreadLocalRandom.current().nextInt(361);
			boostAnimator = new Animator(26, 1);
		}

		@Override
		public void onTick(float delta) {
			addToX(vecX * delta);
			addToY(vecY * delta);
			boostAnimator.calculate(delta);
			if(!boostAnimator.finished()) {
				float boost = 3.5f;
				addToX(vecX * boost * delta);
				addToX(vecY * boost * delta);
			}
			rotation += randomRotation * delta;
			polygon.translate((int) (getX() - polygon.getBounds().getX()), (int) (getY() - polygon.getBounds().getY()));
		}

		@Override
		public void onPaint(Graphics2D g2d) {
			g2d.setColor(new Color(21, 21, 21));
			AffineTransform transform = g2d.getTransform();
			g2d.rotate(rotation, polygon.getBounds().getCenterX(), polygon.getBounds().getCenterY());
			g2d.fill(polygon);
			g2d.setTransform(transform);
		}
		
	}
	
}
