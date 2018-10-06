package projekt.mathe.game.mathespiel.scenes.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class OpenSafeScene extends Scene {

	private Animator animator;
	
	private static final Image bg = ResLoader.getImageByName("game/tresor.jpg");
	
	public OpenSafeScene(Game container) {
		super(container, "safe_end", Values.SCENE_BG_COLOR);
		animator = new Animator(160, 1);
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
					Saver.clearPlayerState();
					Saver.setData("gamefinished", true);
					callScene("ending", getDataForNextScene(), 240f);
				}
			};
			Card card1 = new Card("[Lösungen für die Mathematik-Schulaufgabe]");
			card1.setTextColor(Color.GRAY);
			dialog.addCard(card1);
			Card card2 = new Card("...");
			card2.setTextColor(Color.GRAY);
			dialog.addCard(card2);
			Card card3 = new Card("- Ich weiß überhaupt nicht, ob meine Schüler den Stoff beherrschen.");
			card3.setTextColor(Color.GRAY);
			dialog.addCard(card3);
			Card card4 = new Card("- Deshalb werde ich diesen Zettel einfach einschließen und das Gerücht verbreiten, er würde die Lösungen beinhalten.");
			card4.setTextColor(Color.GRAY);
			dialog.addCard(card4);
			Card card5 = new Card("- Vielleicht versuchen ja ein paar Schüler ihn heimlich zu bekommen und geraten dabei an ein paar Lehrer, die ihnen den Mathe-Stoff näherbringen.");
			card5.setTextColor(Color.GRAY);
			dialog.addCard(card5);
			Card card6 = new Card("- Falls du, der das gerade liest, einer dieser Schüler bist: Sei froh, denn jetzt beherrschst du das Bruch-, Winkel- und Dezimalrechnen etwas mehr.");
			card6.setTextColor(Color.GRAY);
			dialog.addCard(card6);
			Card card7= new Card("...");
			card7.setTextColor(Color.GRAY);
			dialog.addCard(card7);
			Card card8= new Card("- Mehr habe ich nicht zu sagen.");
			card8.setTextColor(Color.GRAY);
			dialog.addCard(card8);
			world.openDialog(dialog);
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(bg, 0, 0, null);
		if(animator.finished()) {
			fillScene(g2d, Color.BLACK, .2f);
		}
	}

	@Override
	public SceneData getDataForNextScene() {
		return new MainSceneData();
	}

}
