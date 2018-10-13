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

public class ChemieWorld extends World{

	public ChemieWorld(Scene container, MapPlayer player) {
		super(container, player);
		addLoadingZone(new LoadingZone(800, 670, 50, 150, this, "aula", 20f, "chemieUnten"));
		addLoadingZone(new LoadingZone(800, -180, 50, 150, this, "aula", 20f, "chemieOben"));
		CutTile.addSplicedTiles(container, this, -500, -500, true, Helper.getImagesBySplices("game/tiles/chemie/lower/unten_01", 783, "png"), 27);
		CutTile.addSplicedTiles(container, this, -500, -500, false, Helper.getImagesBySplices("game/tiles/chemie/upper/oben_01", 783, "png"), 27);
		Barrier.addBarriersFromFile(ResLoader.getFile("game/tiles/chemie/barrier.txt"), this);
	
		addEntity(new Table(container, this, -75, 140));
		addEntity(new Table(container, this, -75, 190));
		addEntity(new Table(container, this, -75, 240));
		addEntity(new Table(container, this, -75, 290));
		addEntity(new Table(container, this, -75, 340));
		addEntity(new Table(container, this, -75, 390));
		
		addEntity(new Table(container, this, 125, 140));
		addEntity(new Table(container, this, 125, 190));
		addEntity(new Table(container, this, 125, 240));
		addEntity(new Table(container, this, 125, 290));
		addEntity(new Table(container, this, 125, 340));
		addEntity(new Table(container, this, 125, 390));
		
		addEntity(new Can(container, this, -430, -200));
		addEntity(new Can(container, this, 170, -250));
		addEntity(new Can(container, this, 670, -50));
		addEntity(new Can(container, this, -220, 350));
		addEntity(new Can(container, this, -170, 800));
		addEntity(new Can(container, this, 670, 650));
		addEntity(new Can(container, this, 270, 120));
		
		Dialog dialog = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card = new Card("Liebe Chemie- und Physiklehrer, der neue Lehrplan sieht vor, die Spaltung von Atomen auch praktisch im Unterricht durchzuführen. Viele Grüße! - Die Schulleitung");
		card.setTextColor(new Color(255, 93, 0));
		dialog.addCard(card);
		addEntity(new SignEntity(container, this, 50, 50, dialog));
		
		Dialog dialog2 = new Dialog(this) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card2 = new Card("Der Nobelpreis für Biologie geht dieses Jahr an Peter Jonason für die Forschung an der Selbstverliebtheit von Nachteulen.");
		card2.setTextColor(new Color(255, 93, 0));
		dialog2.addCard(card2);
		addEntity(new SignEntity(container, this, -200, -271, dialog2));
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
