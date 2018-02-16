package projekt.mathe.game.mathespiel.scenes.game.world.worlds;

import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.barrier.Barrier;
import projekt.mathe.game.mathespiel.scenes.game.world.loadingzone.LoadingZone;
import projekt.mathe.game.mathespiel.scenes.game.world.tiles.CutTile;

public class AulaWorld extends World{

	public AulaWorld(Scene container, MapPlayer player) {
		super(container, player);
		addLoadingZone(new LoadingZone(550, 1230, 200, 50, this, "pausenhof", 20f, "aulaAusgang"));
		addLoadingZone(new LoadingZone(-549, 980, 50, 170, this, "chemie", 20f, "chemieOben"));
		addLoadingZone(new LoadingZone(-270, 1480, 140, 50, this, "chemie", 20f, "chemieUnten"));
		addLoadingZone(new LoadingZone(-450, -180, 50, 200, this, "tische", 20f, "tischeEingang"));
		CutTile.addSplicedTiles(container, this, -500, -500, true, Helper.getImagesBySplices("game/tiles/aula/lower/unten_01", 1968, "png"), 48);
		CutTile.addSplicedTiles(container, this, -500, -500, false, Helper.getImagesBySplices("game/tiles/aula/upper/oben_01", 1968, "png"), 48);
		Barrier.addBarriersFromFile(ResLoader.getFile("game/tiles/aula/barrier.txt"), this);
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
