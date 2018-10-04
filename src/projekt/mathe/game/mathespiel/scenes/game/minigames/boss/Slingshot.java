package projekt.mathe.game.mathespiel.scenes.game.minigames.boss;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import projekt.mathe.game.engine.GameScene;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.engine.help.ResLoader;

public class Slingshot extends ScreenElement{

	private static final Image schleuder = ResLoader.getImageByName("game/minigames/boss/schleuder.png");
	
	private Rectangle rect;
	private boolean dragging;
	private Point lastMousePoint;
	private float angle;
	
	private boolean snapping;
	private float vectorX, vectorY;

	private ProjectileHolder projectileHolder;
	
	private Parabola parabola;
	
	private Color rectColor;
	
	public Slingshot(Scene container) {
		super(container, 586, 420, 108, 300);
		rect = new Rectangle(620, 455, 40, 30);
		angle = 0;
		projectileHolder = new ProjectileHolder(container);
		parabola = new Parabola(container);
		rectColor = new Color(21, 12, 21);
	}

	private void snap() {
		if(rect.getY() < 450) {
			rect.setBounds(620, 455, 40, 30);
			snapping = false;
		}else {
			snapping = true;
		}
		parabola.setActive(false);
	}
	
	@Override
	public void onTick(float delta) {
		if(snapping) {
			float ratio = 0.25f;
			rect.setBounds(Math.round(rect.x - vectorX * delta * ratio), Math.round(rect.y - vectorY * delta * ratio), rect.width, rect.height);
			if(rect.getLocation().distanceSq(new Point(620, 450)) <= 400 || rect.getY() < 450 || (vectorX < 0 && rect.x + rect.width/2 > 620) || (vectorX > 0 && rect.x + rect.width/2 < 620)) {
				rect.setBounds(620, 455, 40, 30);
				snapping = false;
				Projectile projectile = new Projectile(getContainer(), 640, 470);
				projectile.launch(vectorX, vectorY);
				projectileHolder.addElement(projectile);
			}
		}
		projectileHolder.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		parabola.onPaint(g2d);
		projectileHolder.onPaint(g2d);
		g2d.drawImage(schleuder, (int) getX(), (int) getY(), null);
		g2d.setColor(new Color(107, 1, 1));
		g2d.setStroke(new BasicStroke(5f));
		g2d.drawLine(598, 466, rect.x, rect.y + rect.height / 2);
		g2d.drawLine(677, 466, rect.x + rect.width, rect.y + rect.height / 2);
		g2d.setColor(rectColor);
		g2d.fillRoundRect(rect.x, rect.y, rect.width, rect.height, 5, 5);
		g2d.setColor(Color.BLACK);
		g2d.setStroke(new BasicStroke(5f));
		g2d.drawRoundRect(rect.x, rect.y, rect.width, rect.height, 5, 5);
		g2d.fillOval((int) (rect.getCenterX() - 5), (int) (rect.getCenterY() - 5), 10, 10);
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		if(dragging && lastMousePoint != null && !snapping) {
			rect.setBounds(rect.x + e.getX() - lastMousePoint.x, rect.y + e.getY() - lastMousePoint.y, rect.width, rect.height);
			if(rect.getY() >= 450) {
				parabola.calculateCoords(rect.x - 620, rect.y - 450, 100);
				parabola.setActive(true);
			}else {
				parabola.setActive(false);
			}
		}
		lastMousePoint = e.getPoint();
		super.onMouseDragged(e);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		if(rect.contains(e.getPoint())) {
			dragging = true;
			rectColor = new Color(0, 123, 255);
		}
		lastMousePoint = e.getPoint();
		super.onMousePressed(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		lastMousePoint = e.getPoint();
		rectColor = rect.getBounds().contains(e.getPoint()) ? new Color(0, 123, 255) : new Color(21, 12, 21);
		super.onMouseMoved(e);
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		if(dragging && (rect.x != 620 || rect.y != 455)) {
			dragging = false;
			vectorX = rect.x - 620;
			vectorY = rect.y - 450;
			snap();
		}
		rectColor = new Color(21, 12, 21);
		super.onMouseReleased(e);
	}
	
	@Override
	public void onMouseExited(MouseEvent e) {
		onMouseReleased(e);
		super.onMouseExited(e);
	}
	
	public class ProjectileHolder extends Holder<Projectile> {

		private ArrayList<Projectile> destroyingProjectiles;
		
		public ProjectileHolder(Scene container) {
			super(container);
			destroyingProjectiles = new ArrayList<>();
		}
		
		@Override
		public void onTick(float delta) {
			for(Projectile projectile : new ArrayList<>(getElements())) {
				if(projectile.wasDestroyed()) {
					getElements().remove(projectile);
					destroyingProjectiles.remove(projectile);
				}else if(projectile.isDestroying() && !destroyingProjectiles.contains(projectile)) {
					destroyingProjectiles.add(projectile);
					boolean hit = ((BossGame) ((GameScene) container).getMouseRegisteredMiniGame()).getWand().getFensterHolder().hit((int) projectile.getX(), (int) projectile.getY());
					if(hit) {
						getElements().remove(projectile);
						destroyingProjectiles.remove(projectile);
					}
				}
			}
			super.onTick(delta);
		}
		
	}
	
}
