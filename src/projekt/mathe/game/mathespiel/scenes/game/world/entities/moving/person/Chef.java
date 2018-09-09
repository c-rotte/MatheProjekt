package projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person;

import java.awt.Point;

import javax.jws.WebParam;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class Chef extends Person {

	public Chef(Scene container, World world, int x, int y) {
		super(container, world, TYPE.COOK, x, y, 3);
	}

	@Override
	public void onInteract(MapPlayer player) {
		if(Saver.containsData("currCode") && Saver.containsData("safeCode") && Saver.getString("currCode").charAt(1) == Saver.getString("safeCode").charAt(1)) {
			Dialog dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					
				}
				@Override
				public void onFinished(Card lastcard) {
					
				}
			};
			dialog.addCard(new Card("Vielen Dank für deine Hilfe!"));
			world.openDialog(dialog);
		}else {
			Dialog dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					if(lastcard.selected.equals("Nein")) {
						Dialog dialog = new Dialog(world) {
							@Override
							public void onSelected(Card lastcard, boolean finished) {
								
							}
							@Override
							public void onFinished(Card lastcard) {
								
							}
						};
						dialog.addCard(new Card("Dann lass mich in Ruhe weinen!"));
						world.openDialog(dialog);
					}else {
						Dialog dialog = new Dialog(world) {
							@Override
							public void onSelected(Card lastcard, boolean finished) {
								
							}
							@Override
							public void onFinished(Card lastcard) {
								world.container.callScene("angle", world.container.getDataForNextScene(), 40f);
							}
						};
						dialog.addCard(new Card("Gott sei Dank! Lass uns keine Zeit verlieren!"));
						world.openDialog(dialog);
					}
				}
				@Override
				public void onFinished(Card lastcard) {
					
				}
			};
			dialog.addCard(new Card("Ich bin verloren! Was soll ich nur machen?"));
			dialog.addCard(new Card("..."));
			dialog.addCard(new Card("..."));
			Card card = new Card("Oh, wer bist du? Kannst du mir helfen?");
			card.addSelection("Äh...", "Nein");
			dialog.addCard(card);
			world.openDialog(dialog);
		}
	}

	@Override
	public void onTick(float delta) {
		MapPlayer mapPlayer = world.player;
		if(mapPlayer.playerController.isActivated() && !world.isDialogOpen()) {
			float playerMidX = (float) mapPlayer.getOriginalBounds().getCenterX();
			float playerMidY = (float) mapPlayer.getOriginalBounds().getCenterY();
			
			float midX = (float) getBounds().getCenterX();
			float midY = (float) getBounds().getCenterY();
			
			float xDiff = playerMidX - midX;
			float yDiff = playerMidY - midY;
			
			if(Math.abs(xDiff) <= 60 || Math.abs(yDiff) <= 60) {
				if(Math.abs(xDiff) > Math.abs(yDiff)) {
					if(xDiff < 0) {
						setDirection("left");
					}else {
						setDirection("right");
					}
				}else {
					if(yDiff < 0) {
						setDirection("up");
					}else {
						setDirection("down");
					}
				}
			}
		}
		super.onTick(delta);
	}
	
}
