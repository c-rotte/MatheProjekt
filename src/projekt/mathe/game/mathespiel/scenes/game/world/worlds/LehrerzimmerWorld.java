package projekt.mathe.game.mathespiel.scenes.game.world.worlds;

import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.barrier.Barrier;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Can;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Table;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person.FemaleSign;
import projekt.mathe.game.mathespiel.scenes.game.world.loadingzone.LoadingZone;
import projekt.mathe.game.mathespiel.scenes.game.world.tiles.CutTile;

public class LehrerzimmerWorld extends World {

	public LehrerzimmerWorld(Scene container, MapPlayer player) {
		super(container, player);
		
		addLoadingZone(new LoadingZone(400, 260, 200, 60, this, "tische", 20f, "lehrerzimmer"));
		CutTile.addSplicedTiles(container, this, -500, -500, true, Helper.getImagesBySplices("game/tiles/lehrerzimmer/lower/unten_01", 459, "png"), 27);
		CutTile.addSplicedTiles(container, this, -500, -500, false, Helper.getImagesBySplices("game/tiles/lehrerzimmer/upper/oben_01", 459, "png"), 27);
		Barrier.addBarriersFromFile(ResLoader.getFile("game/tiles/lehrerzimmer/barrier.txt"), this);
		
		addEntity(new FemaleSign(container, this, 475, -365));
		
		addEntity(new Table(container, this, 350, -330));
		addEntity(new Table(container, this, 550, -330));
		
		addEntity(new Table(container, this, -350, -270));
		addEntity(new Table(container, this, -250, -270));
		addEntity(new Table(container, this, -150, -270));
		
		addEntity(new Table(container, this, -350, -120));
		addEntity(new Table(container, this, -250, -120));
		addEntity(new Table(container, this, -150, -120));
		
		addEntity(new Table(container, this, -350, 30));
		addEntity(new Table(container, this, -250, 30));
		addEntity(new Table(container, this, -150, 30));
		
		addEntity(new Can(container, this, -350, -30));
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
