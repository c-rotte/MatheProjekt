package projekt.mathe.game.mathespiel.scenes.game.world.worlds;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.barrier.Barrier;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Border;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Can;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Safe;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.SignEntity;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.Table;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person.Boss;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person.Female;
import projekt.mathe.game.mathespiel.scenes.game.world.loadingzone.LoadingZone;
import projekt.mathe.game.mathespiel.scenes.game.world.tiles.CutTile;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class SekWorld extends World{

	private Female female;
	private Boss boss;
	
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
		
		boss = new Boss(container, this);
		
		female = new Female(container, this, 100, 0, boss);
		addEntity(female);
		
		//
		addEntity(new Table(container, this, 1350, 420));
		
		//
		addEntity(new Table(container, this, 850, -280));
		addEntity(new Table(container, this, 950, -280));
		addEntity(new Table(container, this, 1300, -280));
		addEntity(new Table(container, this, 1400, -280));
		addEntity(new Table(container, this, 1650, -280));
		addEntity(new Table(container, this, 1750, -280));
		addEntity(new Table(container, this, 1650, -180));
		addEntity(new Table(container, this, 1750, -180));
		addEntity(new Table(container, this, 1750, -80));
		addEntity(new Table(container, this, 1750, 20));
		
		//sek
		addEntity(new Table(container, this, 100, -280));
		addEntity(new Table(container, this, 200, -280));
		addEntity(new Table(container, this, 300, -280));
		
		addEntity(new Table(container, this, 100, -130));
		addEntity(new Table(container, this, 200, -130));
		addEntity(new Table(container, this, 300, -130));
		addEntity(new Table(container, this, 500, -130));
		addEntity(new Table(container, this, 600, -130));
		
		//Rektor
		addEntity(new Table(container, this, 900, 820));
		addEntity(new Table(container, this, 1000, 820));
		addEntity(new Table(container, this, 1100, 820));
		addEntity(new Table(container, this, 1200, 820));
		
		addEntity(new Table(container, this, 1400, 820));
		addEntity(new Table(container, this, 1500, 820));
		
		addEntity(new Table(container, this, 1600, 820));
		
		addEntity(new Table(container, this, 1850, 820));
		
		//cans
		addEntity(new Can(container, this, -278,-358));
		addEntity(new Can(container, this, -320,-346));
		addEntity(new Can(container, this, -299,-308));
		addEntity(new Can(container, this, -59,523));
		addEntity(new Can(container, this, 1679,-323));
		addEntity(new Can(container, this, 1756,-327));
		addEntity(new Can(container, this, 1698,-206));
		addEntity(new Can(container, this, 1473,501));
		addEntity(new Can(container, this, 1437,-314));
		addEntity(new Can(container, this, 1475,-334));
		addEntity(new Can(container, this, 888,-329));
		addEntity(new Can(container, this, 1648,473));
		
		//
		
		Dialog dialog0 = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card0 = new Card("Treppenhaus");
		card0.setTextColor(new Color(255, 93, 0));
		dialog0.addCard(card0);
		addEntity(new SignEntity(container, this, -250, 231, dialog0));
		
		Dialog dialog2 = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card2 = new Card("Links: Sekretariat");
		card2.setTextColor(new Color(255, 93, 0));
		dialog2.addCard(card2);
		addEntity(new SignEntity(container, this, 850, 31, dialog2));
		
		Dialog dialog3 = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card3 = new Card("Links: Zimmer des stellvertretenden Direktors");
		card3.setTextColor(new Color(255, 93, 0));
		dialog3.addCard(card3);
		Card card3_1 = new Card("Rechts: Zimmer der Oberstufenkoordinatoren");
		card3_1.setTextColor(new Color(255, 93, 0));
		dialog3.addCard(card3_1);
		addEntity(new SignEntity(container, this, 1150, 31, dialog3));
		
		Dialog dialog4 = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card4 = new Card("Auf den Tresor hat NUR der Direktor Zugriff. Auch wenn der Code teilweise bekannt ist: Niemandem ist es gestattet, den Tresor zu öffnen!");
		card4.setTextColor(new Color(255, 83, 71));
		dialog4.addCard(card4);
		Card card4_1 = new Card("- Der Direktor");
		card4_1.setTextColor(new Color(255, 83, 71));
		dialog4.addCard(card4_1);
		addEntity(new SignEntity(container, this, 1500, 631, dialog4));
		
	}

	public void onCall() {
		boss.onCall();
	}
	
	public Boss getBoss() {
		return boss;
	}
	
	public Female getFemale() {
		return female;
	}
	
	@Override
	public void onTick(float delta) {
		boss.onTick(delta);
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
		boss.onPaint(g2d);
	}

}
