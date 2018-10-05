package projekt.mathe.game.mathespiel.scenes.game.world.worlds;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.barrier.Barrier;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.SignEntity;
import projekt.mathe.game.mathespiel.scenes.game.world.loadingzone.LoadingZone;
import projekt.mathe.game.mathespiel.scenes.game.world.tiles.CutTile;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class PausenhofWorld extends World{

	public PausenhofWorld(Scene container, MapPlayer player) {
		super(container, player);
		addLoadingZone(new LoadingZone(965, -113, 150, 50, this, "aula", 20f, "pausenhofEingang"));
		CutTile.addSplicedTiles(container, this, -500, -500, true, Helper.getImagesBySplices("game/tiles/pausenhof/lower/unten_01", 1750, "png"), 50);
		CutTile.addSplicedTiles(container, this, -500, -500, false, Helper.getImagesBySplices("game/tiles/pausenhof/upper/oben_01", 1750, "png"), 50);
		Barrier.addBarriersFromFile(ResLoader.getFile("game/tiles/pausenhof/barrier.txt"), this);
	}

	@Override
	public void onTick(float delta) {
		
	}

	@Override
	public void onPaintBelow(Graphics2D g2d) {
		g2d.setColor(new Color(164, 155, 157));
		g2d.fillRect(-500, -500, 2500, 1750);
	}
	
	@Override
	public void onPaintMiddle(Graphics2D g2d) {
		
	}
	
	@Override
	public void onPaintOnTop(Graphics2D g2d) {
		container.fillScene(g2d, new Color(0, 54, 142), .5f);
	}

}
