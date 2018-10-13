package projekt.mathe.game.mathespiel.scenes.game.world.worlds;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.barrier.Barrier;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Can;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.SignEntity;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Table;
import projekt.mathe.game.mathespiel.scenes.game.world.loadingzone.LoadingZone;
import projekt.mathe.game.mathespiel.scenes.game.world.tiles.CutTile;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class TischeWorld extends World{

	public TischeWorld(Scene container, MapPlayer player) {
		super(container, player);
		
		CutTile.addSplicedTiles(container, this, -500, -500, true, Helper.getImagesBySplices("game/tiles/tische/lower/unten_01", 638, "png"), 22);
		CutTile.addSplicedTiles(container, this, -500, -500, false, Helper.getImagesBySplices("game/tiles/tische/upper/oben_01", 638, "png"), 22);
		
		Barrier.addBarriersFromFile(ResLoader.getFile("game/tiles/tische/barrier.txt"), this);
		
		addLoadingZone(new LoadingZone(-205, -180, 50, 200, this, "aula", 20f, "aulaEingang"));
		addLoadingZone(new LoadingZone(520, -280, 50, 180, this, "sek", 20f, "sekEingang"));
		addLoadingZone(new LoadingZone(100, -450, 150, 50, this, "lehrerzimmer", 20f, "lehrerzimmerEingang"));
	
		addEntity(new Table(container, this, -450, 200));
		addEntity(new Table(container, this, -350, 200));
		addEntity(new Table(container, this, -250, 200));
		addEntity(new Table(container, this, -150, 200));
	
		addEntity(new Table(container, this, 100, 720));
		addEntity(new Table(container, this, 200, 720));
		
		addEntity(new Can(container, this, -370, 600));
		addEntity(new Can(container, this, -350, 610));
		addEntity(new Can(container, this, -380, 640));
		addEntity(new Can(container, this, -420, 610));
	
		Dialog dialog = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card = new Card("Eingang zum Lehrerzimmer");
		card.setTextColor(new Color(255, 93, 0));
		dialog.addCard(card);
		dialog.addCard(new Card("Hinweis für alle Lehrer: Der Stundenplan kann jetzt auch nachts abgerufen werden."));
		addEntity(new SignEntity(container, this, 250, -419, dialog));
		
		Dialog dialog2 = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card2 = new Card("Rechts: Treppenhaus, Sekretariat und Direktorzimmer");
		card2.setTextColor(new Color(255, 93, 0));
		dialog2.addCard(card2);
		addEntity(new SignEntity(container, this, 450, -419, dialog2));
	}

	@Override
	public void onTick(float delta) {
		
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
