package projekt.mathe.game.mathespiel.scenes.game.world.worlds;

import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.barrier.Barrier;
import projekt.mathe.game.mathespiel.scenes.game.world.loadingzone.LoadingZone;
import projekt.mathe.game.mathespiel.scenes.game.world.tiles.CutTile;

public class SekWorld extends World{

	public SekWorld(Scene container, MapPlayer player) {
		super(container, player);
		CutTile.addSplicedTiles(container, this, -500, -500, true, Helper.getImagesBySplices("game/tiles/sek/lower/unten_01", 1600, "png"), 50);
		CutTile.addSplicedTiles(container, this, -500, -500, false, Helper.getImagesBySplices("game/tiles/sek/upper/oben_01", 1600, "png"), 50);
		Barrier.addBarriersFromFile(ResLoader.getFile("game/tiles/sek/barrier.txt"), this);
		addLoadingZone(new LoadingZone(-500, 320, 30, 220, this, "tische", 20f, "tischeEingang"));
	}

	@Override
	public void onTick(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPaintBelow(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPaintMiddle(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPaintOnTop(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

}
