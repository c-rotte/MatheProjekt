package projekt.mathe.game.mathespiel.scenes.game.world.entities;

import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class EntityHolder extends Holder<Entity>{

	private World world;
	
	public EntityHolder(Scene container, World world) {
		super(container);
		this.world = world;
	}
	
	public void onPaintBelow(Graphics2D g2d) {
		for(Entity entity : getElements()) {
			if(entity.belowPlayer) {
				entity.onPerformacePaint(g2d);
			}
		}
	}
	
	public void onPaintOnTop(Graphics2D g2d) {
		for(Entity entity : getElements()) {
			if(!entity.belowPlayer) {
				entity.onPerformacePaint(g2d);
			}
		}
	}
	
	public Entity playerInteract(MapPlayer player) {
		Entity interactEntity = doesPlayerInteractCollide(player);
		if(interactEntity != null) {
			interactEntity.onInteract(player);
		}
		return interactEntity;
	}
	
	public Entity doesPlayerCollide(MapPlayer player) {
		for(Entity entity : getElements()) {
			if(entity.collider) {
				if(entity.onScreen()) {
					if(entity.getBounds().intersects(player.getBounds())) {
						return entity;
					}
				}
			}
		}
		return null;
	}
	
	public Entity doesPlayerInteractCollide(MapPlayer player) {
		if(world.lastFrameDialogOpen()) {
			return null;
		}
		for(Entity entity : getElements()) {
			if(entity.collider) {
				if(entity.onScreen()) {
					if(entity.getBounds().intersects(player.getInteractionBounds())) {
						return entity;
					}
				}
			}
		}
		return null;
	}
	
}
