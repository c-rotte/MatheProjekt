package projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs;

import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;
import projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs.Dialog.Card;

public class MovingTestDialog extends Dialog{

	public MovingTestDialog(World world) {
		super(world);
		Card card1 = new Card("Das ist ein Test für das Matherennen. Möchtest du anfangen?");
		card1.addSelection("ja", "nein");
		addCard(card1);
	}

	@Override
	public void onSelected(Card lastcard, boolean finished) {
		if(finished) {
			if(lastcard.selected.equals("ja")) {
				world.container.callScene("race", world.container.getDataForNextScene(), 40f);
			}
		}
	}

	@Override
	public void onFinished(Card lastcard) {
		
	}

}
