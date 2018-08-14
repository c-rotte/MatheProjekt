package projekt.mathe.game.mathespiel.scenes.game.world.worlds;

import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.barrier.Barrier;
import projekt.mathe.game.mathespiel.scenes.game.world.loadingzone.LoadingZone;
import projekt.mathe.game.mathespiel.scenes.game.world.tiles.CutTile;

public class ChemieWorld extends World{

	public ChemieWorld(Scene container, MapPlayer player) {
		super(container, player);
		addLoadingZone(new LoadingZone(800, 670, 50, 150, this, "aula", 20f, "chemieUnten"));
		addLoadingZone(new LoadingZone(800, -180, 50, 150, this, "aula", 20f, "chemieOben"));
		CutTile.addSplicedTiles(container, this, -500, -500, true, Helper.getImagesBySplices("game/tiles/chemie/lower/unten_01", 783, "png"), 27);
		CutTile.addSplicedTiles(container, this, -500, -500, false, Helper.getImagesBySplices("game/tiles/chemie/upper/oben_01", 783, "png"), 27);
		Barrier.addBarriersFromFile(ResLoader.getFile("game/tiles/chemie/barrier.txt"), this);
	}

	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaintBelow(Graphics2D g2d) {
	
	}

	@Override
	public void onPaintMiddle(Graphics2D g2d) {
	
	}

	@Override
	public void onPaintOnTop(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

}
