package projekt.mathe.game.mathespiel.scenes.game.world.worlds;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;
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
		
		Dialog dialog = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				if(finished) {
					if(lastcard.selected.equals("ja")) {
						world.container.callScene("pyramid", world.container.getDataForNextScene(), 40f);
					}
				}
			}
			@Override
			public void onFinished(Card lastcard) {}
		};
		Card card1 = new Card("Das ist ein Test für das Pyramidenspiel. Möchtest du beginnen?");
		card1.addSelection("ja", "nein");
		dialog.addCard(card1);
		addEntity(new SignEntity(container, this, 1445, 520, dialog));
		
		Dialog dialog2 = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				if(finished) {
					if(lastcard.selected.equals("ja")) {
						world.container.callScene("board", world.container.getDataForNextScene(), 40f);
					}
				}
			}
			@Override
			public void onFinished(Card lastcard) {}
		};
		Card card = new Card("Das ist ein Test für das Tafelspiel. Möchtest du beginnen?");
		card.addSelection("ja", "nein");
		dialog2.addCard(card);
		addEntity(new SignEntity(container, this, 1555, 520, dialog2));
		
		Dialog dialog3 = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				if(finished) {
					if(lastcard.selected.equals("ja")) {
						world.container.callScene("angle", world.container.getDataForNextScene(), 40f);
					}
				}
			}
			@Override
			public void onFinished(Card lastcard) {}
		};
		Card card2 = new Card("Das ist ein Test für das Pizzaspiel. Möchtest du beginnen?");
		card2.addSelection("ja", "nein");
		dialog3.addCard(card2);
		addEntity(new SignEntity(container, this, 1665, 520, dialog3));
		
		Dialog dialog4 = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				if(finished) {
					if(lastcard.selected.equals("ja")) {
						world.container.callScene("boss", world.container.getDataForNextScene(), 40f);
					}
				}
			}
			@Override
			public void onFinished(Card lastcard) {}
		};
		Card card3 = new Card("Das ist ein Test für den Bosskampf. Möchtest du beginnen?");
		card3.addSelection("ja", "nein");
		dialog4.addCard(card3);
		addEntity(new SignEntity(container, this, 1775, 520, dialog4));
		
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
		
	}

}
