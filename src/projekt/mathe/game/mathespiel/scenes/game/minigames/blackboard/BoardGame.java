package projekt.mathe.game.mathespiel.scenes.game.minigames.blackboard;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.minigame.MiniGame;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;

public class BoardGame extends MiniGame{

	private static Image tafel = ResLoader.getImageByName("game/minigames/board/tafel.jpg");
	
	private CalculationHolder calculationHolder;
	private int success;
	
	private TimeBar timeBar;
	
	private Schwamm schwamm;
	
	public BoardGame(Scene container) {
		super(container, "board");
		success = 0;
		calculationHolder = new CalculationHolder(container);
		loadNewCalcs(120, 120, 77, 77);
		timeBar = new TimeBar(container, 95, 600, 1090, 25, 60 * 5 * 12, 8);
		schwamm = new Schwamm(container, 640, 360);
	}

	@Override
	public void onTick(float delta) {
		calculationHolder.onTick(delta);
		if(calculationHolder.completed() && !container.world.isDialogOpen() && !container.fading) {
			success++;
			setMouseBlocked(true);
			container.world.openDialog(new ContinueDialog(container.world, success, 3));
		}else {
			if(!calculationHolder.completed() && timeBar.isFinished() && !container.world.isDialogOpen() && !container.fading) {
				setMouseBlocked(true);
				container.world.openDialog(new FailDialog(container.world));
			}else if(!container.world.isDialogOpen()){
				timeBar.onTick(delta);
			}
		}
		schwamm.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(tafel, 0, 0, null);
		calculationHolder.onPaint(g2d);
		timeBar.onPaint(g2d);
		Helper.drawStringAroundPosition(640, 35, "Tafeln gewischt: " + success, Color.WHITE, 25, FONT.VCR, g2d, null, -1);
		schwamm.onPaint(g2d);
	}

	public void loadNewCalcs(int startX, int startY, int xOff, int yOff) {
		calculationHolder.clear();
		for(int i = 0; i < 4; i++) {
			for(int o = 0; o < 3; o++) {
				calculationHolder.addElement(Calculation.generateInstance(container, startX + i * (200 + xOff), startY + o * (100 + yOff), ThreadLocalRandom.current().nextBoolean()));
			}
		}
		
		if(!calculationHolder.containsBoth()) {
			loadNewCalcs(startX, startY, xOff, yOff);
		}
	}
	
	@Override
	public void onMouseClicked(MouseEvent e) {
		if(!isMouseBlocked()) {
			calculationHolder.onMouseClicked(e);
			schwamm.onMouseClicked(e);
		}
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		if(!isMouseBlocked()) {
			calculationHolder.onMouseDragged(e);
			schwamm.onMouseDragged(e);
		}
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		if(!isMouseBlocked()) {
			calculationHolder.onMouseMoved(e);
			schwamm.onMouseMoved(e);
		}
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		if(!isMouseBlocked()) {
			calculationHolder.onMousePressed(e);
			schwamm.onMousePressed(e);
		}
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		if(!isMouseBlocked()) {
			calculationHolder.onMouseReleased(e);
			schwamm.onMouseReleased(e);
		}
	}
	
	private class FailDialog extends Dialog {

		public FailDialog(World world) {
			super(world);
			Card card1 = new Card("Das war leider zu langsam. Möchtest du es weiter versuchen?");
			card1.addSelection("Ja", "Nein");
			addCard(card1);
		}

		@Override
		public void onSelected(Card lastcard, boolean finished) {
			if(lastcard.selected.equals("Nein")) {
				world.closeDialog();
				MainSceneData mainSceneData = (MainSceneData) container.getDataForNextScene();
				mainSceneData.setMinigameCompleted(false);
				container.callScene("pausenhof", mainSceneData, 40f);
			}else {
				Dialog dialog = new Dialog(world) {
					
					@Override
					public void onSelected(Card lastcard, boolean finished) {
						
					}
					
					@Override
					public void onFinished(Card lastcard) {
						loadNewCalcs(120, 120, 77, 77);
						timeBar.reset();
						setMouseBlocked(false);
					}
				};
				dialog.addCard(new Card("Super! Hier kommt die nächste Tafel!"));
				world.openDialog(dialog);
			}
		}

		@Override
		public void onFinished(Card lastcard) {
			world.closeDialog();
			timeBar.reset();
			loadNewCalcs(120, 120, 77, 77);
		}
		
	}
	
	private class ContinueDialog extends Dialog {

		private int success;
		private int max;
		
		public ContinueDialog(World world, int success, int max) {
			super(world);
			this.success = success;
			this.max = max;
			if(success == max) {
				addCard(new Card("Super! Du hast es geschafft!"));
			}else {
				addCard(new Card("Super! Das war Tafel Nummer " + success + "! Es verbleiben nur noch " + (max - success) + "."));
			}
		}

		@Override
		public void onSelected(Card lastcard, boolean finished) {
		
		}

		@Override
		public void onFinished(Card lastcard) {
			world.closeDialog();
			setMouseBlocked(false);
			if(success == max) {
				MainSceneData mainSceneData = (MainSceneData) container.getDataForNextScene();
				mainSceneData.setMinigameCompleted(true);
				container.callScene("pausenhof", mainSceneData, 40f);
			}else {
				loadNewCalcs(120, 120, 77, 77);
				timeBar.reset();
			}
		}
		
	}
	
}
