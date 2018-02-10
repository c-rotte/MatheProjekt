package projekt.mathe.game.mathespiel.scenes.game.world.tiles;

import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.elements.Holder;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class Tileholder extends Holder<Tile>{

	private World world;
	
	public Tileholder(Scene container, World world) {
		super(container);
		this.world = world;
	}
	
	public void onPaintBelow(Graphics2D g2d) {
		for(Tile tile : getElements()) {
			if(tile.belowPlayer) {
				tile.onPerformacePaint(g2d);
			}
		}
	}
	
	public void onPaintOnTop(Graphics2D g2d) {
		for(Tile tile : getElements()) {
			if(!tile.belowPlayer) {
				tile.onPerformacePaint(g2d);
			}
		}
	}
	
	@Override
	public void onPaint(Graphics2D g2d) {
		//empty
	}
	
}
