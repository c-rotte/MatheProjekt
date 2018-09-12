package projekt.mathe.game.mathespiel.scenes.game.world.worlds;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.barrier.Barrier;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Border;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Safe;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.SignEntity;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person.Female;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person.FemaleSign;
import projekt.mathe.game.mathespiel.scenes.game.world.loadingzone.LoadingZone;
import projekt.mathe.game.mathespiel.scenes.game.world.tiles.CutTile;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class SekWorld extends World{

	public SekWorld(Scene container, MapPlayer player) {
		super(container, player);
		CutTile.addSplicedTiles(container, this, -500, -500, true, Helper.getImagesBySplices("game/tiles/sek/lower/unten_01", 1600, "png"), 50);
		CutTile.addSplicedTiles(container, this, -500, -500, false, Helper.getImagesBySplices("game/tiles/sek/upper/oben_01", 1600, "png"), 50);
		Barrier.addBarriersFromFile(ResLoader.getFile("game/tiles/sek/barrier.txt"), this);
		addLoadingZone(new LoadingZone(-500, 320, 30, 220, this, "tische", 20f, "tischeEingang"));
		
		Dialog borderDialog = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {}
			@Override
			public void onFinished(Card lastcard) {}
		};
		Card card = new Card("Aufgrund von Renovierungsarbeiten im zweiten und dritten Stock werden ab sofort die Klassen 5 bis 10 zusammen mit der Oberstufe im Container unterrichtet.");
		card.setTextColor(new Color(255, 93, 0));
		borderDialog.addCard(card);
		addEntity(new SignEntity(container, this, -175, -263, borderDialog));
		
		addEntity(new Border(container, this, -240, -316));
		addEntity(new Safe(container, this, 1700, 370));
		
		addEntity(new Female(container, this, 100, 0));
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
