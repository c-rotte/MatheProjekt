package projekt.mathe.game.mathespiel.scenes.game.world.entities.moving;

import java.util.ArrayList;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Entity;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public abstract class MovingEntity extends Entity{

	/*
	 * Nur gerade Bewegungslinien!
	 */
	
	private ArrayList<int[]> aims;
	private int[] currAim;
	private float speed;
	
	public MovingEntity(Scene container, World world, int x, int y, int w, int h, float speed, boolean collider, boolean belowPlayer) {
		super(container, world, x, y, w, h, collider, belowPlayer);
		aims = new ArrayList<>();
		this.speed = speed;
	}

	public void addAim(int xAim, int yAim) {
		int[] i = {xAim, yAim};
		aims.add(i);
		if(aims.size() == 1) {
			currAim = i;
		}
	}
	
	private void nextAim() {
		if(aims.indexOf(currAim) == aims.size() - 1) {
			currAim = aims.get(0);
		}else {
			currAim = aims.get(aims.indexOf(currAim) + 1);
		}
	}
	
	public void moveToAim(float delta) {
		if(currAim == null) {
			return;
		}
		if(Math.abs(x - currAim[0]) <= speed && Math.abs(y - currAim[1]) <= speed) {
			nextAim();
		}
		float oldX = x;
		if(x - currAim[0] < -speed) {
			x += speed * delta;
		}else if(x - currAim[0] > speed) {
			x -= speed * delta;
		}
		if(world.player.getBounds().intersects(getBounds())) {
			x = oldX;
		}
		float oldY = y;
		if(y - currAim[1] < -speed) {
			y += speed * delta;
		}else if(y - currAim[1] > speed) {
			y -= speed * delta;
		}
		if(world.player.getBounds().intersects(getBounds())) {
			y = oldY;
		}
	}
}
