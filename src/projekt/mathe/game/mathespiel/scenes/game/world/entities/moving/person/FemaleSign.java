package projekt.mathe.game.mathespiel.scenes.game.world.entities.moving.person;

import java.awt.Color;

import projekt.mathe.game.engine.Scene;
import projekt.mathe.game.engine.save.Saver;
import projekt.mathe.game.mathespiel.scenes.game.world.entities.SignEntity;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class FemaleSign extends SignEntity{

	private static Dialog dialog;
	
	public FemaleSign(Scene container, World world, int x, int y) {
		super(container, world, x, y, getDialog(world));
		
	}

	private static final Dialog getDialog(World world) {
		if(dialog == null) {
			dialog = new Dialog(world) {
				@Override
				public void onSelected(Card lastcard, boolean finished) {
					boolean b = true;
					for(int i = 0; i < 5; i++) {
						if(Saver.containsData("currCode") && Saver.containsData("safeCode")) {
							if(Saver.getString("currCode").charAt(i) != Saver.getString("safeCode").charAt(i)) {
								b = false;
								break;
							}
						}else {
							b = false;
							break;
						}
					}
					if(b) {
						if(lastcard.selected.equals("3")) {
							Dialog dialog = new Dialog(world) {
								@Override
								public void onSelected(Card lastcard, boolean finished) {
									
								}
								@Override
								public void onFinished(Card lastcard) {
									Saver.setData("readFemale", true);
								}
							};
							dialog.addCard(new Card("Richtig, die Fläche eines Dreiecks berechnet man mit [0,5 • g • h]."));
							dialog.addCard(new Card("Der Plan für morgen:"));
							dialog.addCard(new Card("Herr Langsam: Unterricht ab 12:15"));
							dialog.addCard(new Card("Frau Dr. Ama: Unterricht ab 10:40"));
							Card card = new Card("Frau Zeit: Unterricht ab 7:50");
							card.setTextColor(Color.BLUE);
							dialog.addCard(card);
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
							dialog.addCard(new Card("Falsche Antwort. Sie sind kein Lehrer!"));
							Card card = new Card("Zugriff verweigert!");
							card.setTextColor(Color.RED);
							dialog.addCard(card);
							world.openDialog(dialog);
						}
					}else {
						Dialog dialog = new Dialog(world) {
							@Override
							public void onSelected(Card lastcard, boolean finished) {
								
							}
							@Override
							public void onFinished(Card lastcard) {
								
							}
						};
						dialog.addCard(new Card("Der Vertretungsplan für morgen wurde noch nicht festgelegt. Komme später wieder."));
						world.openDialog(dialog);
					}
				}
				@Override
				public void onFinished(Card lastcard) {
					
				}
			};
			Card card = new Card("[Stundenplan der Lehrer]");
			card.setTextColor(Color.BLUE);
			dialog.addCard(card);
			dialog.addCard(new Card("Ich stelle nun eine Testfragen zur Authentifikation."));
			Card card2 = new Card("Wie viele Ecken hat eine Figur, deren Fläche man mit [0,5 • g • h] berechnet?");
			card2.addSelection("2", "3", "5", "4");
			dialog.addCard(card2);
		}
		return dialog;
	}
	
}
