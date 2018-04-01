package projekt.mathe.game.mathespiel.scenes.game.minigames.pyramid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.GameScene;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Camera;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class PyramidScene extends GameScene{

	private PyramidGame pyramidGame;
	private MainSceneData mainSceneData;
	
	public PyramidScene(Game container) {
		super(container, "pyramid", Values.SCENE_BG_COLOR);
		pyramidGame = new PyramidGame(this);
		registerMiniGameMouseEvents(pyramidGame);
		registerWorld(new PyramidWorld(this, null));
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		mainSceneData = (MainSceneData) sceneData;
		pyramidGame.setMouseBlocked(true);
		pyramidGame.renewPyramid();
		world.openDialog(new ExplanationDialog(world));
	}

	@Override
	public SceneData getDataForNextScene() {
		mainSceneData.setMinigameCompleted(true);
		return mainSceneData;
	}

	@Override
	public void onTick(float delta) {
		pyramidGame.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		pyramidGame.onPaint(g2d);
	}

	private class ExplanationDialog extends Dialog{

		public ExplanationDialog(World world) {
			super(world);
			Card card1 = new Card("Es funktioniert wie eine Rechenpyramide. Finde einfach den Block, der die SUMME der beiden unteren Blöcke enthält.");
			addCard(card1);
			Card card2 = new Card("Mit der Maus kannst du den Block dann an die richtige Position schieben. Du hast es geschafft, wenn alle Blöcke am richtigen Ort sind.");
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
	
	private class GoDialog extends Dialog{

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
			pyramidGame.setMouseBlocked(false);
		}
		
	}
	
}
