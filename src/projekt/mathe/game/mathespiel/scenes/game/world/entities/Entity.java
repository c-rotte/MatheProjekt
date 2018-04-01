package projekt.mathe.game.mathespiel.scenes.game.world.entities;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.ScreenElement;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public abstract class Entity extends ScreenElement{

	public boolean collider, belowPlayer;
	public World world;
	
	public Entity(Scene container, World world, int x, int y, int w, int h, boolean collider, boolean belowPlayer) {
		super(container, x, y, w, h);
		this.world = world;
		this.collider = collider;
		this.belowPlayer = belowPlayer;
	}

	public void moveX(int x) {
		addToX(x);
	}
	
	public void moveY(int y) {
		addToY(y);
	}

	public abstract void onInteract(MapPlayer player);
	
}
