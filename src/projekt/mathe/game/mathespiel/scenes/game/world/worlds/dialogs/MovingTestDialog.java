package projekt.mathe.game.mathespiel.scenes.game.world.worlds.dialogs;

import projekt.mathe.game.mathespiel.scenes.game.world.worlds.World;

public class MovingTestDialog extends Dialog{

	public MovingTestDialog(World world) {
		super(world);
		Card card = new Card("Hallo Freund.");
		Card card2 = new Card("Wie gehts?.");
		Card card3 = new Card("Fein.");
		Card card4 = new Card("bye.");
		addCard(card);
		addCard(card2);
		addCard(card3);
		addCard(card4);
	}

	@Override
	public void onSelected(Card lastcard, boolean finished) {
		
	}

}
