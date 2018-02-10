package projekt.mathe.game.mathespiel.scenes.game.world.worlds;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.barrier.Barrier;
import projekt.mathe.game.mathespiel.scenes.game.world.loadingzone.LoadingZone;
import projekt.mathe.game.mathespiel.scenes.game.world.tiles.CutTile;

public class ChemieWorld extends World{

	public ChemieWorld(Scene container, MapPlayer player) {
		super(container, player);
		addLoadingZone(new LoadingZone(800, 670, 50, 120, this, "aula", 20f, "chemieUnten"));
		addLoadingZone(new LoadingZone(800, -180, 50, 120, this, "aula", 20f, "chemieOben"));
		CutTile.addSplicedTiles(container, this, -500, -500, true, Helper.getImagesBySplices("game/tiles/chemie/lower/unten_01", 756, "png"), 27);
		CutTile.addSplicedTiles(container, this, -500, -500, false, Helper.getImagesBySplices("game/tiles/chemie/upper/oben_01", 756, "png"), 27);
		Barrier.addBarriersFromFile(ResLoader.getFile("game/tiles/chemie/barrier.txt"), this);
	}

	@Override
	public void onTick(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPaintBelow(Graphics2D g2d) {
		g2d.setColor(Values.SCENE_BG_COLOR);
		g2d.fillRect(799, 898, 51, 200);
		g2d.fillRect(849, -500, 400, 1600);
	}

	@Override
	public void onPaintMiddle(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.fillRect(793, 895, 3, 200);
	}

	@Override
	public void onPaintOnTop(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

}
