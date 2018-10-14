package projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.game.player.MapPlayer;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class PETeacher extends Person {

	private String state; //normal, activated, success
	
	public PETeacher(Scene container, World world, int x, int y) {
		super(container, world, TYPE.SPORTS, x, y, 3);
		state = "normal";
		addPlayerListenerArea(980, -35, 110, 30);
	}

	public void setState(String state) {
		this.state = state;
		Saver.setData("peState", state);
		switch (state) {
			case "normal" : 
				setX(760);
				setY(995);
				setDirection("left");
				break;
				
			default :
				setX(750);
				setY(10);
				setDirection("right");
				break;
		}
	}
	
	@Override
	protected void aimChanged(int[] oldAim, int[] newAim) {
		super.aimChanged(oldAim, newAim);
		setMoving(false);
		Dialog dialog = new Dialog(world) {
			@Override
			public void onSelected(Card lastcard, boolean finished) {
				Dialog dialog = new Dialog(world) {
					@Override
					public void onSelected(Card lastcard, boolean finished) {
						Dialog dialog = new Dialog(world) {
							@Override
							public void onSelected(Card lastcard, boolean finished) {
								getContainer().callScene("race", getContainer().getDataForNextScene(), 40f);
							}
							@Override
							public void onFinished(Card lastcard) {
								
							}
						};
						Card card3 = new Card("Was? Nun gut, da mir langweilig ist, pass auf: Wenn du mich in einem Rennen besiegst, lasse ich dich ins Gebäude, andernfalls musst du gehen.");
						card3.addSelection("Okay", "Los!");
						dialog.addCard(card3);
						world.openDialog(dialog);;
					}
					@Override
					public void onFinished(Card lastcard) {
						
					}
				};
				Card card2 = new Card("Du darfst hier überhaupt nicht sein! Geh schnell wieder nach Hause!");
				card2.addSelection("Nein!");
				dialog.addCard(card2);
				world.openDialog(dialog);
			}
			@Override
			public void onFinished(Card lastcard) {
				
			}
		};
		Card card = new Card("Nanu? Was machst du denn hier?");
		card.addSelection("Ähm");
		card.addSelection("Ich..");
		dialog.addCard(card);
		
		world.openDialog(dialog);
	}
	
	@Override
	public void onPlayerEntersListenerArea(MapPlayer player) {
		if(state.equals("normal")) {
			state = "activated";
			Saver.setData("peState", "activated");
			player.playerController.setActivated(false);
			player.direction = "down";
			setX(player.getX());
			addAim((int) player.getX(), (int) (player.getY() + player.getH()));
			setMoving(true);
			setDirection("up");
		}else if(state.equals("activated")) {
			player.setY(-42);
			Dialog dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					if(lastcard.selected.equals("Ja")) {
						getContainer().callScene("race", getContainer().getDataForNextScene(), 40f);
					}
				}
				@Override
				public void onFinished(Card lastcard) {
					
				}
			};
			setDirection("right");
			Card card = new Card("Willst du es nochmal versuchen? Denn so kommst du nicht herein!");
			card.addSelection("Ja", "Nein");
			dialog.addCard(card);
			world.openDialog(dialog);
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
		if(state.equals("normal")) {
			Dialog dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					Dialog dialog = new Dialog(world) {
						@Override
						public void onSelected(Card lastcard, boolean finished) {
							
						}
						@Override
						public void onFinished(Card lastcard) {
							
						}
					};
					dialog.addCard(new Card("Wie bitte? Ich bin doch kein Schüler! Ich bin der neue Sportlehrer! Mein Name lautet Herr Langsam. Frechheit!"));
					world.openDialog(dialog);
				}
				@Override
				public void onFinished(Card lastcard) {
					
				}
			};
			Card card = new Card("Was willst du denn?");
			card.addSelection("Hi", "Moin");
			dialog.addCard(card);
			world.openDialog(dialog);
		}else if(state.equals("activated")) {
			Dialog dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					if(lastcard.selected.equals("Ja")) {
						getContainer().callScene("race", getContainer().getDataForNextScene(), 40f);
					}
				}
				@Override
				public void onFinished(Card lastcard) {
					
				}
			};
			Card card = new Card("Willst du es nochmal versuchen? Denn so kommst du nicht herein!");
			card.addSelection("Ja", "Nein");
			dialog.addCard(card);
			world.openDialog(dialog);
		}else if(state.equals("success")) {
			Dialog dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					
				}
				@Override
				public void onFinished(Card lastcard) {
					
				}
			};
			dialog.addCard(new Card("Ich lasse dich schon hinein, keine Sorge."));
			world.openDialog(dialog);
		}
	}
	
}
