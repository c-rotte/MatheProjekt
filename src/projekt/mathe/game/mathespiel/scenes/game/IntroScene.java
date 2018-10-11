package projekt.mathe.game.mathespiel.scenes.game;

import java.awt.Color;
import java.awt.Graphics2D;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class IntroScene extends Scene {

	private Animator animator;
	
	public IntroScene(Game container) {
		super(container, "intro", Color.BLACK);
		animator = new Animator(60, 1);
		registerWorld(World.emptyInstance(this));
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		animator.reset();
	}

	@Override
	public void onTick(float delta) {
		animator.calculate(delta);
		if(animator.finished() && !world.isDialogOpen() && !fading) {
			Dialog dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					
				}
				@Override
				public void onFinished(Card lastcard) {
					callScene("pausenhof", getDataForNextScene(), 70f);
				}
			};
			Card card1 = new Card("...");
			card1.setTextColor(Color.GRAY);
			dialog.addCard(card1);
			Card card2 = new Card("Ob das wirklich eine gute Idee ist?");
			card2.setTextColor(Color.GRAY);
			dialog.addCard(card2);
			Card card3 = new Card("Statt für die Mathe-Schulaufgabe morgen zu lernen, die Lösungen im Schulgebäude zu suchen?");
			card3.setTextColor(Color.GRAY);
			dialog.addCard(card3);
			Card card4 = new Card("Mitten in der Nacht?");
			card4.setTextColor(Color.GRAY);
			dialog.addCard(card4);
			Card card5 = new Card("...");
			card5.setTextColor(Color.GRAY);
			dialog.addCard(card5);
			Card card6 = new Card("Zeit zum Lernen habe ich jetzt keine mehr.");
			card6.setTextColor(Color.GRAY);
			dialog.addCard(card6);
			Card card7= new Card("Na ja.");
			card7.setTextColor(Color.GRAY);
			dialog.addCard(card7);
			Card card8= new Card("Ich werde ja hoffentlich niemanden treffen.");
			card8.setTextColor(Color.GRAY);
			dialog.addCard(card8);
			Card card9 = new Card("Sollte ich doch jemanden treffen, indem ich mit den PFEILTASTEN hin- und herlaufe, kann ich ihn zum Glück mit der LEERTASTE ansprechen.");
			card9.setTextColor(Color.GRAY);
			dialog.addCard(card9);
			world.openDialog(dialog);
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
