package projekt.mathe.game.mathespiel.scenes.game.minigames.race;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import javax.swing.plaf.basic.BasicTreeUI.TreeCancelEditingAction;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.Values;
import projekt.mathe.game.engine.help.Animator;
import projekt.mathe.game.engine.help.ResLoader;
import projekt.mathe.game.engine.minigame.MiniGame;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;

public class RaceGame extends MiniGame{

	private static RaceBackgroundHolder backgroundHolder;
	private RacePlayer racePlayer;
	private RaceEnemy raceEnemy;
	private Questionfield questionfield;
	private Countdown countdown;
	private String state;
	private Rectangle finishLine;
	
	public RaceGame(Scene container) {
		super(container, "race");
		backgroundHolder = new RaceBackgroundHolder(container);	
	}

	public void reset() {
		backgroundHolder.reset();
		racePlayer = new RacePlayer(container, 518, 250);
		raceEnemy = new RaceEnemy(container, 713, 250);
		questionfield = new Questionfield(container,
				() ->{
					racePlayer.boost();
				},
				() ->{
					raceEnemy.boost();
				});
		countdown = new Countdown(container);
		state = "startDialog";
		finishLine = null;
		container.world.openDialog(new StartDialog(container.world));
	}
	
	private class StartDialog extends Dialog{

		public StartDialog(World world) {
			super(world);
			addCard(new Card("Bla bla tut"));
		}

		@Override
		public void onSelected(Card lastcard, boolean finished) {
			
		}

		@Override
		public void onFinished(Card lastcard) {
			state = "countdown";
		}
		
	}
	
	@Override
	public void onTick(float delta) {
		racePlayer.onTick(delta);
		raceEnemy.onTick(delta);
		backgroundHolder.onTick(delta);
		questionfield.onTick(delta);
		if(!state.equals("startDialog")) {
			countdown.onTick(delta);
		}
		if(countdown.started() && state.equals("countdown")) {
			racePlayer.startRunning();
			raceEnemy.startRunning();
			state = "running";
			questionfield.start();
		}
		if(state.equals("startdialog")) {
			
		}else if(state.equals("running")) {
			if(questionfield.finished()) {
				state = "racefinished";
				int distance = 1000;
				finishLine = new Rectangle(390, (int) (racePlayer.getY() < raceEnemy.getY() ? racePlayer.getY() - distance : raceEnemy.getY() - distance), 500, 5);
			}
		}else if(state.equals("racefinished")) {
			if(raceEnemy.getY() <= finishLine.y && raceEnemy.getSprintingState().equals("sprinting")) {
				raceEnemy.stopRunning();
			}
			if(racePlayer.getY() <= finishLine.y && racePlayer.getSprintingState().equals("sprinting")) {
				racePlayer.stopRunning();
			}
			if(racePlayer.getSprintingState().equals("still") && raceEnemy.getSprintingState().equals("still")) {
				state = "finishLineCrossed";
			}
		}else if(state.equals("finishLineCrossed")) {
			state = "dialog";
			if(questionfield.finishedCorrectly()) {
				container.world.openDialog(new SuccessDialog(container.world));
			}else {
				container.world.openDialog(new FailDialog(container.world));
			}
		}else if(state.equals("enddialog")) {
			
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		backgroundHolder.onPaint(g2d);
		g2d.setColor(Color.WHITE);
		g2d.fillRect(390, 225, 500, 5);
		if(finishLine != null) {
			g2d.fill(finishLine);
		}
		racePlayer.onPerformacePaint(g2d);
		raceEnemy.onPerformacePaint(g2d);
		questionfield.onPerformacePaint(g2d);
		if(!state.equals("startDialog")) {
			countdown.onPerformacePaint(g2d);
		}
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		questionfield.onMouseClicked(e);
	}
	
	@Override
	public void onMouseDragged(MouseEvent e) {
		questionfield.onMouseDragged(e);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		questionfield.onMousePressed(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		questionfield.onMouseMoved(e);
	}
	
	private class SuccessDialog extends Dialog{

		public SuccessDialog(World world) {
			super(world);
			addCard(new Card("Du hast gewonnen!"));
		}

		@Override
		public void onSelected(Card lastcard, boolean finished) {
			
		}

		@Override
		public void onFinished(Card lastcard) {
			MainSceneData mainSceneData = (MainSceneData) container.getDataForNextScene();
			mainSceneData.setMinigameCompleted(true);
			container.callScene("pausenhof", mainSceneData, 40f);
		}
		
	}
	
	private class FailDialog extends Dialog{

		public FailDialog(World world) {
			super(world);
			Card card = new Card("Du hast leider verloren. Möchtest du es nochmal probieren?");
			card.addSelection("Ja", "Nein");
			addCard(card);
		}

		@Override
		public void onSelected(Card lastcard, boolean finished) {
			if(lastcard.selected.equals("Ja")) {
				container.callScene("race", container.getDataForNextScene(), 40f);
			}else {
				MainSceneData mainSceneData = (MainSceneData) container.getDataForNextScene();
				mainSceneData.setMinigameCompleted(false);
				container.callScene("pausenhof", mainSceneData, 40f);
			}
		}

		@Override
		public void onFinished(Card lastcard) {
			
		}
		
	}
	
}
