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
import projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person.Chef;
import projekt.mathe.game.mathespiel.scenes.game.world.loadingzone.LoadingZone;
import projekt.mathe.game.mathespiel.scenes.game.world.tiles.CutTile;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

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
		addEntity(new Chef(container, this, 1430, 945));
		
		addEntity(new Table(container, this, 1400, 570));
		addEntity(new Table(container, this, 1500, 570));
		addEntity(new Table(container, this, 1600, 570));
		addEntity(new Table(container, this, 1700, 570));
		
		addEntity(new Table(container, this, 1300, 1120));
		addEntity(new Table(container, this, 1400, 1120));
		
		addEntity(new Table(container, this, 850, 270));
		addEntity(new Table(container, this, 425, -20));
		addEntity(new Table(container, this, 525, -20));
		
		addEntity(new Can(container, this, -320, 1300));
		addEntity(new Can(container, this, -220, 650));
		addEntity(new Can(container, this, 75, 100));
		addEntity(new Can(container, this, 830, -200));
		addEntity(new Can(container, this, 890, 400));
		addEntity(new Can(container, this, 1475, 500));
		addEntity(new Can(container, this, 1700, 1100));
		addEntity(new Can(container, this, 780, 1145));
		
		Dialog dialog = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card = new Card("An alle Schüler: Getränkedosen sind im Schulgebäude strengstens verboten!");
		card.setTextColor(new Color(255, 93, 0));
		dialog.addCard(card);
		addEntity(new SignEntity(container, this, 600, 831, dialog));
		
		Dialog dialog2 = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card2 = new Card("Links: Erster Stock");
		card2.setTextColor(new Color(255, 93, 0));
		dialog2.addCard(card2);
		addEntity(new SignEntity(container, this, -450, 881, dialog2));
		
		Dialog dialog3 = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card3 = new Card("Eingänge zu den Chemie-, Physik- und Biologiesälen");
		card3.setTextColor(new Color(255, 93, 0));
		dialog3.addCard(card3);
		addEntity(new SignEntity(container, this, -350, -315, dialog3));
		
		Dialog dialog4 = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card4 = new Card("[Kioskpreise]");
		card4.setTextColor(new Color(255, 83, 71));
		dialog4.addCard(card4);
		dialog4.addCard(new Card("Pizza: 5€ (pro Stück)"));
		dialog4.addCard(new Card("Muffin: 3€ (pro Stück)"));
		dialog4.addCard(new Card("Brause: 2€ (pro Stange)"));
		addEntity(new SignEntity(container, this, 275, 120, dialog4));
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
