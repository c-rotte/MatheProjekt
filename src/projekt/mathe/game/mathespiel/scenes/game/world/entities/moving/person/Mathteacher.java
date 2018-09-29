package projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.MainSceneData;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class Mathteacher extends Person {

	private String state; //normal, finished
	
	public Mathteacher(Scene container, World world) {
		super(container, world, TYPE.GLASSES, 370, -155, 4);
		addAim(-350, -155);
		addAim(-350, 680);
		addAim(50, 680);
		addAim(50, 160);
		addAim(50, 680);
		addAim(600, 680);
		addAim(50, 680);
		addAim(50, 160);
		addAim(50, 680);
		addAim(-350, 680);
		addAim(-350, -155);
		addAim(600, -155);
	}

	@Override
	protected void aimChanged(int[] oldAim, int[] newAim) {
		if(oldAim[0] == newAim[0]) {
			if(oldAim[1] > newAim[1]) {
				setDirection("up");
			}else {
				setDirection("down");
			}
		}else if(oldAim[1] == newAim[1]) {
			if(oldAim[0] > newAim[0]) {
				setDirection("left");
			}else {
				setDirection("right");
			}
		}
	}
	
	public void onCall(String lastID, MainSceneData sceneData) {
		if(lastID.equals("aula") || lastID.equals("choose")) {
			if(Saver.containsData("currCode") && Saver.containsData("safeCode") && Saver.getString("currCode").charAt(2) == Saver.getString("safeCode").charAt(2)) {
				state = "finished";
				if(sceneData.getLastLoadingZoneID().equals("chemieOben") || lastID.equals("choose")) {
					setX(-350);
					setY(260);
					setDirection("up");
				}else if(sceneData.getLastLoadingZoneID().equals("chemieUnten")) {
					setX(-350);
					setY(260);
					setDirection("up");
				}
				clearAims();
				
				addAim(-350, -155);
				addAim(600, -155);
				addAim(-350, -155);
				addAim(-350, 680);
				addAim(50, 680);
				addAim(50, 160);
				addAim(50, 680);
				addAim(600, 680);
				addAim(50, 680);
				addAim(50, 160);
				addAim(50, 680);
				addAim(-350, 680);
			}else {
				state = "normal";
			}
			setMoving(true);
		}else if(lastID.equals("board")) {
			setX(-350);
			setY(260);
			setDirection("right");
			world.player.direction = "left";
			world.player.setX(-270);
			world.player.setY(260);
			
			getContainer().camera.focusX(140);
			getContainer().camera.focusY(260);
			
			clearAims();
			
			addAim(-350, -155);
			addAim(600, -155);
			addAim(-350, -155);
			addAim(-350, 680);
			addAim(50, 680);
			addAim(50, 160);
			addAim(50, 680);
			addAim(600, 680);
			addAim(50, 680);
			addAim(50, 160);
			addAim(50, 680);
			addAim(-350, 680);
			
			if(sceneData.minigameCompleted()) {
				state = "finished";
				Dialog dialog = new Dialog(world) {
					@Override
					public void onSelected(Card lastcard, boolean finished) {
						
					}
					@Override
					public void onFinished(Card lastcard) {
						Saver.saveCurrentState(getContainer().player, getContainer());
						StringBuilder builder = new StringBuilder(Saver.getString("currCode"));
						builder.setCharAt(2, Saver.getString("safeCode").charAt(2));
						Saver.setData("currCode", builder.toString());
						setMoving(true);
						setDirection("up");
					}
				};
				dialog.addCard(new Card("Das lief doch ganz gut. Du darfst gehen."));
				dialog.addCard(new Card("..."));
				dialog.addCard(new Card("..."));
				dialog.addCard(new Card("Achso, hier ist deine Bescheinigung, dass du mir geholfen hast."));
				dialog.addCard(new Card("..."));
				dialog.addCard(new Card("Wieso ist denn hier kein Platz für deinen Namen? Komisch."));
				dialog.addCard(new Card("Auf dem Papier steht nur \"" + Saver.getString("safeCode").charAt(2) + "\". Na ja, wenn die Schulleitung das so entschieden hat..."));
				world.openDialog(dialog);
			}
		}
	}
	
	@Override
	public void onTick(float delta) {
		MapPlayer player = world.player;
		switch (state) {
			case "normal":
				
				break;
			case "finished":
				
				break;
		}
		super.onTick(delta);
	}
	
	@Override
	public void onInteract(MapPlayer player) {
		
		final String d = getDirection();
		
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
		switch (state) {
			case "normal":
				setMoving(false);
				Dialog dialog = new Dialog(world) {
					@Override
					public void onSelected(Card lastcard, boolean finished) {
						if(lastcard.selected.equals("Okay")) {
							Dialog dialog = new Dialog(world) {
								@Override
								public void onSelected(Card lastcard, boolean finished) {
									
								}
								@Override
								public void onFinished(Card lastcard) {
									getContainer().callScene("board", getContainer().getDataForNextScene(), 40f);
								}
							};
							dialog.addCard(new Card("Super, los geht's! Folge mir!"));
							world.openDialog(dialog);
						}else {
							world.openDialog(noDialog());
						}
					}
					@Override
					public void onFinished(Card lastcard) {
						
					}
				};
				dialog.addCard(new Card("Diese frechen Schüler! Jetzt muss ich wegen ihnen hier Wache halten. Ich..."));
				dialog.addCard(new Card("..."));
				dialog.addCard(new Card("..."));
				dialog.addCard(new Card("Nanu? Wer bist denn du?"));
				dialog.addCard(new Card("Tja, das muss ich leider melden! Außer..."));
				Card card = new Card("Du könntest mir doch helfen! Dann verrate ich auch nicht, dass du hier bist!");
				card.addSelection("Okay", "Nein");
				dialog.addCard(card);
				world.openDialog(dialog);
				break;
			case "finished":
				setMoving(false);
				Dialog dialog2 = new Dialog(world) {
					@Override
					public void onSelected(Card lastcard, boolean finished) {
						
					}
					@Override
					public void onFinished(Card lastcard) {
						setMoving(true);
						setDirection(d);
						System.out.println(d);
					}
				};
				dialog2.addCard(new Card("Danke für's Tafelwischen, aber ich brauche dich nicht mehr."));
				world.openDialog(dialog2);
				break;
		}
	}

	private Dialog noDialog() {
		Dialog dialog = new Dialog(world) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				if(lastcard.selected.equals("Ja")) {
					Dialog dialog = new Dialog(world) {
						@Override
						public void onSelected(Card lastcard, boolean finished) {
							
						}
						@Override
						public void onFinished(Card lastcard) {
							getContainer().callScene("board", getContainer().getDataForNextScene(), 40f);
						}
					};
					dialog.addCard(new Card("Dann los, folge mir, worauf wartest du?"));
					world.openDialog(dialog);
				}else {
					world.openDialog(noDialog());
				}
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		dialog.addCard(new Card("Ein Nein akzeptiere ich nicht!"));
		Card card = new Card("Ich frage dich nochmal: Hilfst du mir?");
		card.addSelection("Ja", "Nein");
		dialog.addCard(card);
		return dialog;
	}
	
}
