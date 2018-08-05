package projekt.mathe.game.mathespiel.scenes.game.minigames.blackboard;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import projekt.mathe.game.engine.Game;
import projekt.mathe.game.engine.GameScene;
import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.SceneData;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.mathespiel.Main;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class BoardScene extends GameScene{

	private BoardGame boardGame;
	private MainSceneData mainSceneData;
	
	public BoardScene(Game container) {
		super(container, "board", Values.SCENE_BG_COLOR);
		boardGame = new BoardGame(this);
		registerWorld(new BoardWorld(this, null));
	}

	@Override
	public void onCall(String lastID, SceneData sceneData) {
		mainSceneData = new MainSceneData();
		boardGame = new BoardGame(this);
		registerMiniGameMouseEvents(boardGame);
		boardGame.setMouseBlocked(true);
		world.openDialog(new ExplanationDialog(world));
	}

	@Override
	public void onTick(float delta) {
		boardGame.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		boardGame.onPaint(g2d);
	}

	@Override
	public SceneData getDataForNextScene() {
		return mainSceneData;
	}

	private class ExplanationDialog extends Dialog {

		public ExplanationDialog(World world) {
			super(world);
			Card card1 = new Card("Der letzte Sch�ler, der ausgefragt wurde, hat einiges falsch gemacht.");
			addCard(card1);
			Card card2 = new Card("Bitte entferne alle FALSCHEN Rechnungen. Klicke dazu mit dem Schwamm auf die entsprechende Gleichung. Durch ein erneutes Klicken machst du diese Aktion r�ckg�ngig.");
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
			Card card1 = new Card("Na, dann los! Viel Gl�ck!");
			addCard(card1);
		}

		@Override
		public void onSelected(Card lastcard, boolean finished) {

		}

		@Override
		public void onFinished(Card lastcard) {
			boardGame.setMouseBlocked(false);
		}
		
	}
	
}
