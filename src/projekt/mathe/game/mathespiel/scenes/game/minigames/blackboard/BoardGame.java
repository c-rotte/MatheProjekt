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
	
	private Schwamm schwamm;
	
	public BoardGame(Scene container) {
		super(container, "board");
		success = 0;
		calculationHolder = new CalculationHolder(container);
		loadNewCalcs(120, 128, 77, 77);
		schwamm = new Schwamm(container, 640, 360);
	}

	@Override
	public void onTick(float delta) {
		calculationHolder.onTick(delta);
		if(calculationHolder.completed() && !container.world.isDialogOpen() && !container.fading) {
			success++;
			setMouseBlocked(true);
			container.world.openDialog(new ContinueDialog(container.world, success, 2));
		}
		schwamm.onTick(delta);
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		g2d.drawImage(tafel, 0, 0, null);
		calculationHolder.onPaint(g2d);
		Helper.drawStringAroundPosition(640, 35, "Tafeln gewischt: " + success + " von 2", Color.WHITE, 25, FONT.VCR, g2d, null, -1);
		Helper.drawStringAroundPosition(640, 680, "Falsche Rechnungen: " + calculationHolder.getWrong(), Color.RED, 25, FONT.VCR, g2d, null, -1);
		schwamm.onPaint(g2d);
	}

	public void loadNewCalcs(int startX, int startY, int xOff, int yOff) {
		calculationHolder.clear();
		for(int i = 0; i < 4; i++) {
			for(int o = 0; o < 3; o++) {
				boolean b = ThreadLocalRandom.current().nextBoolean();
				calculationHolder.addElement(Calculation.generateInstance(container, startX + i * (200 + xOff), startY + o * (100 + yOff), b));
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
				addCard(new Card("Super! Das war die erste Tafel."));
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
				container.callScene("chemie", mainSceneData, 40f);
			}else {
				loadNewCalcs(120, 128, 77, 77);
			}
		}
		
	}
	
}
