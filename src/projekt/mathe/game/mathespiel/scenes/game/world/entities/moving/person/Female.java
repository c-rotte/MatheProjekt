package projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person;

import java.awt.event.MouseWheelEvent;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class Female extends Person {

	public Female(Scene container, World world, int x, int y) {
		super(container, world, TYPE.FEMALE, x, y, 3);
		setDirection("down");
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
			
			if(Math.abs(xDiff) <= 50 || Math.abs(yDiff) <= 50) {
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
	}
	
	@Override
	public void onInteract(MapPlayer player) {
		switch (player.direction) {
			case "up": 
				setDirection("down");
				break;
			case "down":
				setDirection("up");
				break;
			case "left":
				setDirection("right");
				break;
			case "right":
				setDirection("left");
				break;
		}
		if(Saver.containsData("currCode") && Saver.containsData("safeCode") && Saver.getString("currCode").charAt(4) == Saver.getString("safeCode").charAt(4)) {
			Dialog dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					
				}
				@Override
				public void onFinished(Card lastcard) {
					
				}
			};
			dialog.addCard(new Card("7:50 also; dann bereite ich mich mal auf den Unterricht vor..."));
			world.openDialog(dialog);
		}else {
			Dialog dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					if(lastcard.selected.equals("7:50")) {
						Dialog dialog = new Dialog(world) {
							@Override
							public void onSelected(Card lastcard, boolean finished) {
								
							}
							@Override
							public void onFinished(Card lastcard) {
								StringBuilder builder = new StringBuilder(Saver.getString("currCode"));
								builder.setCharAt(4, Saver.getString("safeCode").charAt(4));
								Saver.setData("currCode", builder.toString());
							}
						};
						dialog.addCard(new Card("Ja! Das war es! Um 7:50 beginnt meine Mathestunde!"));
						dialog.addCard(new Card("Das notiere ich mir gleich... Nanu? Du hast wohl ein Blatt verloren... \"" + Saver.getString("safeCode").charAt(4) + "\"... Merkwürdig."));
						world.openDialog(dialog);
					}else if(lastcard.selected.equals("...")){
						Dialog dialog = new Dialog(world) {
							@Override
							public void onSelected(Card lastcard, boolean finished) {
								
							}
							@Override
							public void onFinished(Card lastcard) {
								
							}
						};
						dialog.addCard(new Card("Ich verstehe dich nicht..."));
						world.openDialog(dialog);
					}else if(lastcard.selected.equals("nie")){
						Dialog dialog = new Dialog(world) {
							@Override
							public void onSelected(Card lastcard, boolean finished) {
								
							}
							@Override
							public void onFinished(Card lastcard) {
								
							}
						};
						dialog.addCard(new Card("Sehr witzig!"));
						world.openDialog(dialog);
					}else {
						Dialog dialog = new Dialog(world) {
							@Override
							public void onSelected(Card lastcard, boolean finished) {
								
							}
							@Override
							public void onFinished(Card lastcard) {
								
							}
						};
						dialog.addCard(new Card("Nein, ich glaube, das war es nicht..."));
						world.openDialog(dialog);
					}
				}
				@Override
				public void onFinished(Card lastcard) {
					
				}
			};
			dialog.addCard(new Card("Oh, hallo! Sind Sie der neue Lehrer? Freut mich, Sie kennenzulernen. Ich heiße Frau Zeit."));
			dialog.addCard(new Card("Moment, du bist ja ein Schüler! Tut mir leid."));
			Card card = new Card("Ich bin ein bisschen verwirrt zur Zeit, weißt du zufällig, wann ich Unterricht habe?");
			card.addSelection("8:40", "12:20", Saver.containsData("readFemale") && Saver.getBoolean("readFemale") ? "7:50" : "...", "13:25", "nie", "11:30");
			dialog.addCard(card);
			world.openDialog(dialog);
		}
	}
	
	
	
}
