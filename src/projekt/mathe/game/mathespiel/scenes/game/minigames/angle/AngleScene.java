package projekt.mathe.game.mathespiel.scenes.game.minigames.angle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.GameScene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class AngleScene extends GameScene{

	private MainSceneData mainSceneData;
	private AngleGame angleGame;
	
	public AngleScene(Game container) {
		super(container, "angle", Color.WHITE);
		registerWorld(World.emptyInstance(this));
		angleGame = new AngleGame(this);
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		mainSceneData = (MainSceneData) sceneData;
		angleGame = new AngleGame(this);
		registerMiniGameMouseEvents(angleGame);
		world.openDialog(new ExplanationDialog(world));
	}

	@Override
	public void onTick(float delta) {
		angleGame.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		angleGame.onPaint(g2d);
	}

	@Override
	public SceneData getDataForNextScene() {
		return mainSceneData;
	}
	
	private class ExplanationDialog extends Dialog {

		public ExplanationDialog(World world) {
			super(world);
			Card card1 = new Card("Bei der Pizzabestellung des Kiosks ist ein Fehler aufgetreten; nun wurden nur halbe Pizzen geliefert. Diese müssen jetzt genau aufgeteilt werden.");
			addCard(card1);
			Card card2 = new Card("Für jede Pizza hast du 10 Sekunden Zeit. Bitte versuche, durch Drücken und Ziehen der Maus den angegebenen Winkel zu erraten.");
			addCard(card2);
			Card card3 = new Card("Hast du alles verstanden?");
			card3.addSelection("Ja", "Nein");
			addCard(card3);
		}

		@Override
		public void onSelected(Card lastcard, boolean finished) {
			if(finished) {
				if(lastcard.selected.equals("Ja")) {
					world.openDialog(new GoDialog(world).reset());
				}else {
					world.openDialog(this.reset());
				}
			}
		}

		@Override
		public void onFinished(Card lastcard) {
			
		}
		
	}
	
	private class GoDialog extends Dialog {

		public GoDialog(World world) {
			super(world);
			Card card1 = new Card("Na, dann los! Viel Glück!");
			addCard(card1);
		}

		@Override
		public void onSelected(Card lastcard, boolean finished) {

		}

		@Override
		public void onFinished(Card lastcard) {
			
		}
		
	}

}
