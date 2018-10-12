package projekt.mathe.game.mathespiel.scenes.game.world.worlds;

import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.barrier.Barrier;
import projekt.mathe.game.mathespiel.scenes.game.world.barrier.BarrierHolder;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Entity;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.EntityHolder;
import projekt.mathe.game.mathespiel.scenes.game.world.loadingzone.LoadingZone;
import projekt.mathe.game.mathespiel.scenes.game.world.loadingzone.LoadingZoneHolder;
import projekt.mathe.game.mathespiel.scenes.game.world.tiles.Tile;
import projekt.mathe.game.mathespiel.scenes.game.world.tiles.Tileholder;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;

public abstract class World{

	/*
	 * Hält Entites, Tiles, Loadingzones und Barriers
	 */
	
	public Scene container;
	public Tileholder tileholder;
	public EntityHolder entityholder;
	public MapPlayer player;
	private Dialog dialog;
	private LoadingZoneHolder loadingZoneHolder;
	private BarrierHolder barrierHolder;
	
	private int lastFrameDialogOpen;
	
	public World(Scene container, MapPlayer player) {
		this.player = player;
		this.container = container;
		tileholder = new Tileholder(container, this);
		entityholder = new EntityHolder(container, this);
		loadingZoneHolder = new LoadingZoneHolder(container);
		barrierHolder = new BarrierHolder(container);
		lastFrameDialogOpen = 0;
	}

	public final void onWorldTick(float delta) {
		tileholder.onTick(delta);
		entityholder.onTick(delta);
		loadingZoneHolder.onTick(delta);
		if(dialog != null) {
			dialog.onTick(delta);
			lastFrameDialogOpen = 2;
		}else {
			if(lastFrameDialogOpen != 0) {
				lastFrameDialogOpen--;
			}
		}
		onTick(delta);
	}
	
	public boolean lastFrameDialogOpen() {
		return lastFrameDialogOpen != 0;
	}
	
	public abstract void onTick(float delta);
	
	public abstract void onPaintBelow(Graphics2D g2d);
	
	public abstract void onPaintMiddle(Graphics2D g2d);
	
	public abstract void onPaintOnTop(Graphics2D g2d);
	
	public final void onWorldPaintBelow(Graphics2D g2d) {
		onPaintBelow(g2d);
		tileholder.onPaintBelow(g2d);
		entityholder.onPaintBelow(g2d);
		onPaintMiddle(g2d);
	}
	
	public final void onWorldPaintOnTop(Graphics2D g2d) {
		tileholder.onPaintOnTop(g2d);
		entityholder.onPaintOnTop(g2d);
		onPaintOnTop(g2d);
		barrierHolder.onPaint(g2d);
		loadingZoneHolder.onPaint(g2d);
		if(dialog != null) {
			dialog.onPaint(g2d);
		}
	}
	
	public boolean doesPlayerCollideWithBarrier() {
		return barrierHolder.doesCollide(player.getBounds());
	}
	
	public Entity doesPlayerCollideWithEntity() {
		return entityholder.doesPlayerCollide(player);
	}
	
	public Entity doesPlayerInteractCollideWithEntity() {
		return entityholder.doesPlayerInteractCollide(player);
	}
	
	public Entity playerInteract() {
		return entityholder.playerInteract(player);
	}
	
	public void addLoadingZone(LoadingZone loadingZone) {
		loadingZoneHolder.addElement(loadingZone);
	}
	
	public void addBarrier(Barrier barrier) {
		barrierHolder.addElement(barrier);
	}
	
	public void addTile(Tile tile) {
		tileholder.addElement(tile);
	}
	
	public void addEntity(Entity entity) {
		entityholder.addElement(entity);
	}
	
	public boolean isDialogOpen() {
		return dialog != null;
	}
	
	public void openDialog(Dialog dialog) {
		this.dialog = dialog;
	}
	
	public void closeDialog() {
		this.dialog = null;
	}
	
	public static final World emptyInstance(Scene container) {
		return new World(container, null) {
			
			@Override
			public void onTick(float delta) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPaintOnTop(Graphics2D g2d) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPaintMiddle(Graphics2D g2d) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPaintBelow(Graphics2D g2d) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
}
