package projekt.mathe.game.mathespiel.scenes.game.minigames.angle;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.concurrent.ThreadLocalRandom;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.help.Helper;
import projekt.mathe.game.engine.help.Helper.FONT;
import projekt.mathe.game.engine.minigame.MiniGame;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.minigames.blackboard.TimeBar;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class AngleGame extends MiniGame{

	private int pizzen;
	
	private AngleCalculator angleCalculator;
	private TimeBar timeBar;
	private String state; //starting, playing, finished
	
	public AngleGame(Scene container) {
		super(container, "angle");
		angleCalculator = new AngleCalculator(container, 640, 600);
		timeBar = new TimeBar(container, 100, 660, 1080, 30, 20 * 60, 10);
		state = "starting";
		pizzen = 1;
		setMouseBlocked(true);
	}

	@Override
	public void onTick(float delta) {
		switch (state) {
			case "starting":
				setMouseBlocked(true);
				if(!container.world.isDialogOpen() && !container.fading) {
					Dialog dialog = new Dialog(container.world) {
						@Override
						public void onSelected(Card lastcard, boolean finished) {
							
						}
						@Override
						public void onFinished(Card lastcard) {
							timeBar.reset();
							angleCalculator.reset();
							angleCalculator.setCorrectAngle(ThreadLocalRandom.current().nextInt(1, 18) * 10 + (ThreadLocalRandom.current().nextBoolean() ? 0 : 5), false);
							state = "playing";
							setMouseBlocked(false);
						}
					};
					dialog.addCard(new Card("Hier kommt Pizza Nummer " + pizzen + "!"));
					container.world.openDialog(dialog);
				}
				break;
			case "playing":
				timeBar.onTick(delta);
				if(timeBar.isFinished()) {
					angleCalculator.setCorrectAngle(angleCalculator.getCorrectAngle(), true);
					setMouseBlocked(true);
					state = "finished";
				}
				break;
			case "finished":
				angleCalculator.onTick(delta);
				if(angleCalculator.finishedAnimation() && !container.world.isDialogOpen() && !container.fading) {
					Dialog dialog = new Dialog(container.world) {
						@Override
						public void onSelected(Card lastcard, boolean finished) {
							if(lastcard.selected.equals("Ja")) {
								Dialog dialog2 = new Dialog(container.world) {
									
									@Override
									public void onSelected(Card lastcard, boolean finished) {
										
									}
									
									@Override
									public void onFinished(Card lastcard) {
										container.world.closeDialog();
										state = "starting";
									}
								};
								dialog2.addCard(new Card("Super, dann machen wir weiter."));
								container.world.openDialog(dialog2);
							}else {
								MainSceneData mainSceneData = (MainSceneData) container.getDataForNextScene();
								mainSceneData.setMinigameCompleted(false);
								container.callScene("pausenhof", mainSceneData, 40f);
							}
						}
						@Override
						public void onFinished(Card lastcard) {
							container.world.closeDialog();
							if(pizzen >= 5) {
								MainSceneData mainSceneData = (MainSceneData) container.getDataForNextScene();
								mainSceneData.setMinigameCompleted(true);
								container.callScene("pausenhof", mainSceneData, 40f);	
							}else {
								state = "starting";
								pizzen++;
							}
						}
					};
					if(angleCalculator.chosenWasRight()) {
						dialog.addCard(new Card("Der Unterschied betrug nur " + Math.round(Math.abs(angleCalculator.getChosenAngle() - angleCalculator.getCorrectAngle())) + "°. Super!"));
						if(pizzen >= 5) {
							dialog.addCard(new Card("Das war die letzte Pizza. Vielen Dank für deine Hilfe!"));
						}else {
							dialog.addCard(new Card("Dann machen wir mal weiter."));
						}
					}else {
						dialog.addCard(new Card("Der Unterschied war leider zu groß: " + Math.round(Math.abs(angleCalculator.getChosenAngle() - angleCalculator.getCorrectAngle())) + "°!"));
						Card card = new Card("Möchtest du weitermachen?");
						card.addSelection("Ja", "Nein");
						dialog.addCard(card);
					}
					container.world.openDialog(dialog);
				}
				break;
		}
	}

	@Override
	public void onPaint(Graphics2D g2d) {
		angleCalculator.onPaint(g2d);
		Helper.drawStringAroundPosition(640, 35, "Pizza " + pizzen + " von 5", Color.WHITE, 30, FONT.VCR, g2d, Color.BLACK, 5f);
		timeBar.onPaint(g2d);
	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		if(!isMouseBlocked()) {
			angleCalculator.onMouseDragged(e);
		}
		super.onMouseDragged(e);
	}
	
	@Override
	public void onMouseMoved(MouseEvent e) {
		if(!isMouseBlocked()) {
			angleCalculator.onMouseMoved(e);
		}
		super.onMouseMoved(e);
	}
	
	@Override
	public void onMousePressed(MouseEvent e) {
		if(!isMouseBlocked()) {
			angleCalculator.onMousePressed(e);
		}
		super.onMousePressed(e);
	}
	
	@Override
	public void onMouseReleased(MouseEvent e) {
		if(!isMouseBlocked()) {
			angleCalculator.onMouseReleased(e);
		}
		super.onMouseReleased(e);
	}
	
}
